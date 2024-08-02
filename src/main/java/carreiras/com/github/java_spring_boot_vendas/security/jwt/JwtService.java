package carreiras.com.github.java_spring_boot_vendas.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.SpringApplication;
// import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

// import carreiras.com.github.vendas.Application;
import carreiras.com.github.java_spring_boot_vendas.domain.entity.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.security.expiration}")
    private String jwtSecurityExpiration;

    @Value("${jwt.subscription.key}")
    private String jwtSubscriptionKey;

    /*
    Executar este main para testar os métodos:
        - Gerar token (gerarToken) e
        - Validar Token (tokenValido)
    */
    // public static void main(String[] args) {
    //     ConfigurableApplicationContext contexto = SpringApplication.run(Application.class);
    //     JwtService jwtService = contexto.getBean(JwtService.class);
    //     Usuario usuario = Usuario.builder().login("fulano").build();
    //     String token = jwtService.gerarToken(usuario);
    //     System.out.println(token);
    //     boolean tokenValido = jwtService.tokenValido(token);
    //     System.out.println("O token está válido? " + tokenValido);
    //     System.out.println(jwtService.obterLoginUsuario(token));
    // }

    public String gerarToken(Usuario usuario) {
        long minutesExpire = Long.valueOf(this.jwtSecurityExpiration);
        LocalDateTime plusMinutes = LocalDateTime.now().plusMinutes(minutesExpire);
        Instant toInstant = plusMinutes.atZone(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(toInstant);
        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(from)
                .signWith(SignatureAlgorithm.HS512, jwtSubscriptionKey)
                .compact();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date expiration = claims.getExpiration();
            LocalDateTime localDateTime = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(jwtSubscriptionKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }
}

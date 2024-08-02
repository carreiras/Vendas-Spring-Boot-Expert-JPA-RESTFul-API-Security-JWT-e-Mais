package carreiras.com.github.java_spring_boot_vendas.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import carreiras.com.github.java_spring_boot_vendas.domain.entity.Usuario;
import carreiras.com.github.java_spring_boot_vendas.exception.SenhaInvalidaException;
import carreiras.com.github.java_spring_boot_vendas.rest.dto.CredencialDto;
import carreiras.com.github.java_spring_boot_vendas.rest.dto.TokenDto;
import carreiras.com.github.java_spring_boot_vendas.security.jwt.JwtService;
import carreiras.com.github.java_spring_boot_vendas.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@Api("Api Usuários")
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Cria um novo usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "201 CREATED - Requisição de POST bem-sucedida. Irá retornar o item criado."),
            @ApiResponse(code = 400, message = "400 BAD REQUEST - Dados enviados de forma incorreta ou fora do padrão.")
    })
    public Usuario save(@RequestBody @Valid Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioService.save(usuario);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Autentica um usuário existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Usuário autenticado com sucesso"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED - Usuário nao autorizado"),
    })
    public TokenDto authenticate(@RequestBody CredencialDto credencialDTO) {
        try {
            Usuario usuario = Usuario.builder()
                    .login(credencialDTO.getLogin())
                    .senha(credencialDTO.getSenha())
                    .build();
            UserDetails usuarioAutenticado = usuarioService.authenticate(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDto(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}

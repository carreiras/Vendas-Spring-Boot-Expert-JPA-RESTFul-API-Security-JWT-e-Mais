package com.carreiras.vendas.service.impl;

import com.carreiras.vendas.exception.SenhaInvalidaException;
import com.carreiras.vendas.domain.entity.Usuario;
import com.carreiras.vendas.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public UserDetails authenticate(Usuario usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean senhaOk = passwordEncoder.matches(usuario.getSenha(), userDetails.getPassword());

        if (senhaOk)
            return userDetails;

        throw new SenhaInvalidaException("Senha inválida.");
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}

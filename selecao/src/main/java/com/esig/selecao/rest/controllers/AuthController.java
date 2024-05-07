package com.esig.selecao.rest.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.esig.selecao.config.UserAuthProvider;
import com.esig.selecao.rest.dto.authentication.CredentialDTO;
import com.esig.selecao.rest.dto.authentication.SignUpDTO;
import com.esig.selecao.rest.dto.authentication.UserDTO;
import com.esig.selecao.enums.Cargo;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialDTO credentialDTO) {
        Usuario user = authService.login(credentialDTO);

        user.setToken(userAuthProvider.generateToken(user));

        return ResponseEntity.ok(toDto(user));
    }

    private UserDTO toDto(Usuario user) {
        return UserDTO.builder()
        .primeiroNome(user.getPrimeiroNome())
        .sobrenome(user.getSobrenome())
        .cargo(user.getCargo())
        .token(user.getToken())
        .login(user.getLogin())
        .build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDTO signUpDTO) {
        Usuario user = authService.register(signUpDTO);

        user.setToken(userAuthProvider.generateToken(user));

        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(toDto(user));
    }

}

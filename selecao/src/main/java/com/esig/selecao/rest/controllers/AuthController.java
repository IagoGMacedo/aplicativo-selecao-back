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
import com.esig.selecao.enums.Role;
import com.esig.selecao.model.User;
import com.esig.selecao.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialDTO credentialDTO) {
        User user = userService.login(credentialDTO);

        user.setToken(userAuthProvider.generateToken(user));

        return ResponseEntity.ok(toDto(user));
    }

    private UserDTO toDto(User user) {
        return UserDTO.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .role(user.getRole())
        .token(user.getToken())
        .login(user.getLogin())
        .build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDTO signUpDTO) {
        User user = userService.register(signUpDTO);

        user.setToken(userAuthProvider.generateToken(user));

        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(toDto(user));
    }

}

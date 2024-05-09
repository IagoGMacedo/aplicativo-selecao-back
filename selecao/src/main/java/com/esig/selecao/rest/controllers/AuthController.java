package com.esig.selecao.rest.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.esig.selecao.config.UserAuthProvider;
import com.esig.selecao.rest.dto.authentication.CredentialDTO;
import com.esig.selecao.rest.dto.authentication.SignUpDTO;
import com.esig.selecao.rest.dto.authentication.UsuarioDTO;
import com.esig.selecao.enums.Cargo;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserAuthProvider userAuthProvider;

    @Operation(description = "Loga um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna as informações do usuário que foi logado, entre elas o token JWT"),
        @ApiResponse(responseCode = "404", description = "Não existe um usuário com as credenciais informadas")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody CredentialDTO credentialDTO) {
        Usuario user = authService.login(credentialDTO);

        user.setToken(userAuthProvider.generateToken(user));

        return ResponseEntity.ok(toDto(user));
    }

  

    @Operation(description = "Registra um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna as informações do Usuário cadastrado"),
        @ApiResponse(responseCode = "400", description = "Login informado já existe")
    })
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody SignUpDTO signUpDTO) {
        Usuario user = authService.register(signUpDTO);

        user.setToken(userAuthProvider.generateToken(user));

        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(toDto(user));
    }

    private UsuarioDTO toDto(Usuario user) {
        return UsuarioDTO.builder()
                .id(user.getId())
                .primeiroNome(user.getPrimeiroNome())
                .sobrenome(user.getSobrenome())
                .cargo(user.getCargo())
                .token(user.getToken())
                .login(user.getLogin())
                .build();
    }

}

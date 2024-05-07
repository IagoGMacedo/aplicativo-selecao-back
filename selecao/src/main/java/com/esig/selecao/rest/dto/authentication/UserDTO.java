package com.esig.selecao.rest.dto.authentication;

import com.esig.selecao.enums.Cargo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String primeiroNome;
    private String sobrenome;
    private String login;
    private Cargo cargo;
    private String token;
}

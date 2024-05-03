package com.esig.selecao.rest.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO para recebimento de credenciais de cadastro do usuário via endpoint */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDTO {
    private String firstName; 
    private String lastName; 
    private String login; 
    private String password;
    
}

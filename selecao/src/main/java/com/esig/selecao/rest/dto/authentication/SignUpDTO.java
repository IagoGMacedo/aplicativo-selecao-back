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
    private String primeiroNome; 
    private String sobrenome; 
    private String login; 
    private String senha;
    
}

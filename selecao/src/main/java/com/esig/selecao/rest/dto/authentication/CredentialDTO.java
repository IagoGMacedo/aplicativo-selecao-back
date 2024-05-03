package com.esig.selecao.rest.dto.authentication;

/** DTO para recebimento de credenciais de login do usuário via endpoint */
public record CredentialDTO(String login, String password) {

}

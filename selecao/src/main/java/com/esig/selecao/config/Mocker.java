package com.esig.selecao.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.esig.selecao.enums.Prioridade;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.rest.dto.TarefaDTO;
import com.esig.selecao.rest.dto.authentication.SignUpDTO;
import com.esig.selecao.service.AuthService;
import com.esig.selecao.service.TarefaService;
import com.esig.selecao.service.UsuarioService;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Component
public class Mocker implements CommandLineRunner {
    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final TarefaService tarefaService;

    @Override
    public void run(String... args) throws Exception {
       







    }
}

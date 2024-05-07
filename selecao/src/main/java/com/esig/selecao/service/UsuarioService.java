package com.esig.selecao.service;

import java.util.Optional;
import java.util.List;


import org.springframework.data.domain.Example;

import com.esig.selecao.model.Usuario;


public interface UsuarioService {

    public Optional<Usuario> encontrarPeloId(Integer id);
    public Usuario salvar(Usuario usuario);
    public void deletar(Usuario usuario);
    public List<Usuario> encontrarTodos(Example example);
    
}

package com.esig.selecao.service;

import java.util.Optional;
import java.util.List;


import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.esig.selecao.model.Usuario;
import com.esig.selecao.rest.dto.UsuarioValorDTO;
import com.esig.selecao.rest.dto.authentication.UsuarioDTO;


public interface UsuarioService {

    public UsuarioDTO encontrarPeloId(Long id);
    public void deletar(Long id);
    public List<UsuarioDTO> encontrarTodos(Usuario filtro);
    public UsuarioDTO update(Long id, Usuario usuario);
    public UsuarioDTO patch(Long id, Usuario usuarioIncompleto);
    public List<UsuarioValorDTO> encontrarValores();
    
}

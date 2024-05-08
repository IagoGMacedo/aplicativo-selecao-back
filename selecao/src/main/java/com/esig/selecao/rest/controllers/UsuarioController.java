package com.esig.selecao.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.rest.dto.authentication.UsuarioDTO;
import com.esig.selecao.service.UsuarioService;
import com.esig.selecao.utils.Patcher;

@RequestMapping("/api/usuarios")
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Patcher patcher;

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer id) {
        return new ResponseEntity<UsuarioDTO>(usuarioService.encontrarPeloId(id), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return new ResponseEntity<UsuarioDTO>((usuarioService.update(id, usuario)), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UsuarioDTO> patch(@PathVariable Integer id, @RequestBody Usuario usuarioIncompleto) {
        return new ResponseEntity<UsuarioDTO>((usuarioService.patch(id, usuarioIncompleto)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> find(Usuario filtro) {
        return new ResponseEntity<List<UsuarioDTO>>((usuarioService.encontrarTodos(filtro)), HttpStatus.OK);

    }


    
}

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
import com.esig.selecao.rest.dto.UsuarioValorDTO;
import com.esig.selecao.rest.dto.authentication.UsuarioDTO;
import com.esig.selecao.service.UsuarioService;
import com.esig.selecao.utils.Patcher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/api/usuarios")
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Patcher patcher;

    @Operation(description = "Busca usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna o usuário com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe um usuário com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return new ResponseEntity<UsuarioDTO>(usuarioService.encontrarPeloId(id), HttpStatus.OK);
    }

    @Operation(description = "Exclui um usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "O Usuário foi deletado"),
        @ApiResponse(responseCode = "404", description = "Não existe um Usuário com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza um usuário com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado"),
        @ApiResponse(responseCode = "404", description = "Não existe um usuário com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return new ResponseEntity<UsuarioDTO>((usuarioService.update(id, usuario)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza um usuário com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado"),
        @ApiResponse(responseCode = "404", description = "Não existe um usuário com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<UsuarioDTO> patch(@PathVariable Long id, @RequestBody Usuario usuarioIncompleto) {
        return new ResponseEntity<UsuarioDTO>((usuarioService.patch(id, usuarioIncompleto)), HttpStatus.OK);
    }

    @Operation(description = "Lista os usuários existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de usuários conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> find(Usuario filtro) {
        return new ResponseEntity<List<UsuarioDTO>>((usuarioService.encontrarTodos(filtro)), HttpStatus.OK);

    }

    @Operation(description = "Lista os nomes e IDS dos usuários do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de ids e nomes"),

    })
    @GetMapping("/usuarioValor")
    public ResponseEntity<List<UsuarioValorDTO>> findValues() {
        return new ResponseEntity<List<UsuarioValorDTO>>((usuarioService.encontrarValores()), HttpStatus.OK);

    }


    
}

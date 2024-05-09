package com.esig.selecao.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.esig.selecao.model.Tarefa;
import com.esig.selecao.rest.dto.TarefaDTO;
import com.esig.selecao.service.TarefaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/tarefas")
@RestController
@RequiredArgsConstructor
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Operation(description = "Busca tarefa pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a tarefa com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe uma tarefa com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<TarefaDTO> getTarefaById(@PathVariable Integer id) {
        return new ResponseEntity<TarefaDTO>((tarefaService.encontrarPeloId(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova tarefa por DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna a tarefa criada"),
        @ApiResponse(responseCode = "404", description = "O usuário atrelado à tarefa não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<TarefaDTO> save(@RequestBody TarefaDTO tarefa) {
        return new ResponseEntity<TarefaDTO>((tarefaService.salvar(tarefa)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma tarefa pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A tarefa foi deletada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma tarefa com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tarefaService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza uma tarefa com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a tarefa atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma tarefa com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<TarefaDTO> update(@PathVariable Integer id, @RequestBody TarefaDTO tarefa) {
        return new ResponseEntity<TarefaDTO>((tarefaService.update(id, tarefa)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma tarefa com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a tarefa atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma tarefa com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<TarefaDTO> patch(@PathVariable Integer id, @RequestBody TarefaDTO tarefaIncompletaDTO) {
        return new ResponseEntity<TarefaDTO>((tarefaService.patch(id, tarefaIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Lista as tarefas existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de tarefas conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<TarefaDTO>> find(Tarefa filtro) {
        return new ResponseEntity<List<TarefaDTO>>((tarefaService.encontrarTodos(filtro)), HttpStatus.OK);
    }

    @Operation(description = "Lista as tarefas de um usuário a partir de seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de tarefas conforme o ID passado"),
        @ApiResponse(responseCode = "404", description = "Não existe um usuário com ID específicado")

    })
    @GetMapping("/consultarTarefas/{id}")
    public ResponseEntity<List<TarefaDTO>> consultasTarefasUsuario(@PathVariable Integer id) {
        return new ResponseEntity<List<TarefaDTO>>((tarefaService.consultarTarefasUsuario(id)), HttpStatus.OK);
    }

}

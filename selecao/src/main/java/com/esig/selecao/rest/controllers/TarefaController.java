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
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/tarefas")
@RestController
@RequiredArgsConstructor
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("{id}")
    public ResponseEntity<TarefaDTO> getTarefaById(@PathVariable Integer id) {
        return new ResponseEntity<TarefaDTO>((tarefaService.encontrarPeloId(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> save(@RequestBody TarefaDTO tarefa) {
        return new ResponseEntity<TarefaDTO>((tarefaService.salvar(tarefa)), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tarefaService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TarefaDTO> update(@PathVariable Integer id, @RequestBody TarefaDTO tarefa) {
        return new ResponseEntity<TarefaDTO>((tarefaService.update(id, tarefa)), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TarefaDTO> patch(@PathVariable Integer id, @RequestBody TarefaDTO tarefaIncompletaDTO) {
        return new ResponseEntity<TarefaDTO>((tarefaService.patch(id, tarefaIncompletaDTO)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> find(Tarefa filtro) {
        return new ResponseEntity<List<TarefaDTO>>((tarefaService.encontrarTodos(filtro)), HttpStatus.OK);
    }

    @GetMapping("/consultarTarefas/{id}")
    public ResponseEntity<List<TarefaDTO>> consultasTarefasUsuario(@PathVariable Integer id) {
        return new ResponseEntity<List<TarefaDTO>>((tarefaService.consultarTarefasUsuario(id)), HttpStatus.OK);
    }

}

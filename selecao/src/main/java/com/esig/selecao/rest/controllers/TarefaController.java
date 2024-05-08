package com.esig.selecao.rest.controllers;

import java.util.List;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.CollectionUtils;
import com.esig.selecao.exception.AppException;
import com.esig.selecao.model.Tarefa;
import com.esig.selecao.rest.dto.AlteracaoUsuarioDTO;
import com.esig.selecao.rest.dto.TarefaDTO;
import com.esig.selecao.rest.dto.TarefaRetornoDTO;
import com.esig.selecao.service.TarefaService;
import com.esig.selecao.utils.Patcher;
import java.util.stream.Collectors;


import lombok.RequiredArgsConstructor;

@RequestMapping("/api/tarefas")
@RestController
@RequiredArgsConstructor
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private Patcher patcher;

    @GetMapping("{id}")
    public ResponseEntity<TarefaRetornoDTO> getTarefaById(@PathVariable Integer id) {
        Tarefa tarefa = tarefaService
            .encontrarPeloId(id)
            .orElseThrow(() -> // se nao achar lança o erro!
            new AppException("Unknown task", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(toDto(tarefa));
    }

    

    @PostMapping
    public ResponseEntity<TarefaRetornoDTO> save(@RequestBody TarefaDTO tarefa) {
        return new ResponseEntity<TarefaRetornoDTO>(toDto(tarefaService.salvar(tarefa)), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        tarefaService.encontrarPeloId(id)
                .map(tarefa -> {
                    tarefaService.deletar(tarefa);
                    return tarefa;
                })
                .orElseThrow(() -> new AppException("Unknown task", HttpStatus.NOT_FOUND));

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
            @RequestBody Tarefa tarefa) {
        tarefaService
                .encontrarPeloId(id)
                .map(tarefaExistente -> {
                    tarefa.setId(tarefaExistente.getId());
                    tarefaService.salvar(tarefa);
                    return tarefaExistente;
                }).orElseThrow(() -> new AppException("Unknown task", HttpStatus.NOT_FOUND));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody Tarefa tarefaIncompleta) {
        tarefaService
                .encontrarPeloId(id)
                .map(tarefaExistente -> {
                    patcher.patchProperties(tarefaIncompleta, tarefaExistente);
                    tarefaService.salvar(tarefaExistente);
                    return tarefaExistente;
                }).orElseThrow( () ->
                        new AppException("Unknown task", HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<TarefaRetornoDTO> find(Tarefa filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        List<Tarefa> listaTarefas = tarefaService.encontrarTodos(example);

        return toDtoList(listaTarefas);

    }

    @PatchMapping("/alterarUsuario")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUsuarioTarefa(@RequestBody AlteracaoUsuarioDTO dto){
        tarefaService.atualizarUsuario(dto.getIdTarefa(), dto.getIdUsuario());
    }

    @GetMapping("/consultarTarefas/{id}")
    public List<TarefaRetornoDTO> consultasTarefasUsuario(@PathVariable Integer id){
        return toDtoList(tarefaService.consultarTarefasUsuario(id));
    }

    private TarefaRetornoDTO toDto(Tarefa tarefa) {
        return TarefaRetornoDTO.builder()
        .id(tarefa.getId())
        .titulo(tarefa.getTitulo())
        .descricao(tarefa.getDescricao())
        .usuario(tarefa.getUsuario().getPrimeiroNome())
        .prioridade(tarefa.getPrioridade())
        .situacao(tarefa.getSituacao())
        .deadLine(tarefa.getDeadLine().toString())
        .build();
    }

    private List<TarefaRetornoDTO> toDtoList(List<Tarefa> listaTarefas) {
        if(CollectionUtils.isEmpty(listaTarefas)){
            return Collections.emptyList();
        }
        return listaTarefas.stream().map(
                tarefa -> TarefaRetornoDTO
                    .builder()
                    .id(tarefa.getId())
                    .titulo(tarefa.getTitulo())
                    .descricao(tarefa.getDescricao())
                    .usuario(tarefa.getUsuario().getPrimeiroNome())
                    .prioridade(tarefa.getPrioridade())
                    .situacao(tarefa.getSituacao())
                    .deadLine(tarefa.getDeadLine().toString())
                    .build()
        ).collect(Collectors.toList());
    }

}

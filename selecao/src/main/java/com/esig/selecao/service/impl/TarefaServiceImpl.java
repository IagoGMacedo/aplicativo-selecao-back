package com.esig.selecao.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.esig.selecao.enums.Situacao;
import com.esig.selecao.exception.AppException;
import com.esig.selecao.model.Tarefa;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.repository.TarefaRepository;
import com.esig.selecao.repository.UsuarioRepository;
import com.esig.selecao.rest.dto.TarefaDTO;
import com.esig.selecao.service.TarefaService;
import com.esig.selecao.utils.Patcher;

import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TarefaServiceImpl implements TarefaService {
    private final TarefaRepository repository;

    private final UsuarioRepository usuarioRepository;

    private final Patcher patcher;

    @Override
    public TarefaDTO encontrarPeloId(Integer id) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new AppException("Tarefa não encontrada", HttpStatus.NOT_FOUND));
        return toDto(tarefa);
    }

    @Override
    public TarefaDTO salvar(TarefaDTO tarefaDTO) {
        Long idUsuario = tarefaDTO.getUsuario();
        Usuario usuario = usuarioRepository
                .findById(idUsuario)
                .orElseThrow(() -> new AppException("Usuário não encontrado", HttpStatus.NOT_FOUND));

        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTitulo(tarefaDTO.getTitulo());
        novaTarefa.setDescricao(tarefaDTO.getDescricao());
        novaTarefa.setDeadLine(extractLocalDate(tarefaDTO.getDeadLine()));
        novaTarefa.setPrioridade(tarefaDTO.getPrioridade());
        novaTarefa.setSituacao(Situacao.ANDAMENTO);
        novaTarefa.setUsuario(usuario);
        return toDto(repository.save(novaTarefa));
    }

    @Override
    public void deletar(Integer id) {
        Tarefa tarefa = repository
                .findById(id)
                .orElseThrow(() -> new AppException("Tarefa não encontrada", HttpStatus.NOT_FOUND));
        repository.delete(tarefa);
    }

    @Override
    public List<TarefaDTO> encontrarTodos(Tarefa filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDtoList(repository.findAll(example));
    }

    @Override
    public TarefaDTO salvar(Tarefa tarefa) {
        return toDto(repository.save(tarefa));
    }

    @Override
    public List<TarefaDTO> consultarTarefasUsuario(Integer id) {
        return toDtoList(repository.consultarTarefasUsuario(id));
    }

    @Override
    public TarefaDTO patch(Integer id, TarefaDTO tarefaIncompletaDTO) {
        Tarefa tarefaExistente = repository.findById(id)
                .orElseThrow(() -> new AppException("Tarefa não encontrada", HttpStatus.NOT_FOUND));

        Tarefa tarefaIncompleta = extractTarefa(tarefaIncompletaDTO);

        patcher.patchProperties(tarefaIncompleta, tarefaExistente);
        return toDto(repository.save(tarefaExistente));
    }
    

    @Override
    public TarefaDTO update(Integer id, TarefaDTO tarefa) {
        Tarefa tarefaExistente = repository
                .findById(id)
                .orElseThrow(() -> new AppException("Tarefa não encontrada", HttpStatus.NOT_FOUND));

        Tarefa novaTarefa = extractTarefa(tarefa);
        novaTarefa.setId(tarefaExistente.getId());
        return toDto(repository.save(novaTarefa));
    }

    

    private TarefaDTO toDto(Tarefa tarefa) {
        return TarefaDTO.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .usuario(tarefa.getUsuario().getId())
                .nome_usuario(tarefa.getUsuario().getNomeCompleto())
                .prioridade(tarefa.getPrioridade())
                .situacao(tarefa.getSituacao())
                .deadLine(tarefa.getDeadLine().toString())
                .build();
    }

    private List<TarefaDTO> toDtoList(List<Tarefa> listaTarefas) {
        if (CollectionUtils.isEmpty(listaTarefas)) {
            return Collections.emptyList();
        }
        return listaTarefas.stream().map(
                tarefa -> TarefaDTO
                        .builder()
                        .id(tarefa.getId())
                        .titulo(tarefa.getTitulo())
                        .descricao(tarefa.getDescricao())
                        .usuario(tarefa.getUsuario().getId())
                        .nome_usuario(tarefa.getUsuario().getNomeCompleto())
                        .prioridade(tarefa.getPrioridade())
                        .situacao(tarefa.getSituacao())
                        .deadLine(tarefa.getDeadLine().toString())
                        .build())
                .collect(Collectors.toList());
    }

    private LocalDate extractLocalDate(String deadLine) {
        String dataString = "2024-05-07";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dataString, formatter);
    }

    private Tarefa extractTarefa(TarefaDTO tarefaIncompletaDto) {
        Tarefa tarefa = new Tarefa();
        if (tarefaIncompletaDto.getUsuario() != null) {
            Long idUsuario = tarefaIncompletaDto.getUsuario();
            Usuario usuario = usuarioRepository
                    .findById(idUsuario)
                    .orElseThrow(() -> new AppException("Usuário não encontrado", HttpStatus.NOT_FOUND));
            tarefa.setUsuario(usuario);
        }
        tarefa.setTitulo(tarefaIncompletaDto.getTitulo());
        tarefa.setDescricao(tarefaIncompletaDto.getDescricao());
        tarefa.setDeadLine(extractLocalDate(tarefaIncompletaDto.getDeadLine()));
        tarefa.setPrioridade(tarefaIncompletaDto.getPrioridade());
        tarefa.setSituacao(tarefaIncompletaDto.getSituacao());
        return tarefa;
    }

}

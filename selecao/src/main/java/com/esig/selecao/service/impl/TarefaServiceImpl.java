package com.esig.selecao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esig.selecao.enums.Situacao;
import com.esig.selecao.exception.AppException;
import com.esig.selecao.model.Tarefa;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.repository.TarefaRepository;
import com.esig.selecao.repository.UsuarioRepository;
import com.esig.selecao.rest.dto.TarefaDTO;
import com.esig.selecao.service.TarefaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarefaServiceImpl implements TarefaService {
    private final TarefaRepository repository;

    private final UsuarioRepository usuarioRepository;

    @Override
    public Optional<Tarefa> encontrarPeloId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Tarefa salvar(TarefaDTO tarefaDTO) {
        System.out.println("entrou no método salvar tarefa");
        Integer idUsuario = tarefaDTO.getUsuario();
        Usuario usuario = usuarioRepository
                    .findById(idUsuario)
                    .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTitulo(tarefaDTO.getTitulo());
        novaTarefa.setDescricao(tarefaDTO.getDescricao());
        novaTarefa.setDeadLine(tarefaDTO.getDeadLine());
        novaTarefa.setPrioridade(tarefaDTO.getPrioridade());
        novaTarefa.setSituacao(Situacao.ANDAMENTO);
        novaTarefa.setUsuario(usuario);
        repository.save(novaTarefa);
        return novaTarefa;
    }

    @Override
    public void deletar(Tarefa tarefa) {
        repository.delete(tarefa);
    }

    @Override
    public List<Tarefa> encontrarTodos(Example example) {
        return repository.findAll(example);
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    @Override
    public void atualizarUsuario(Integer id, Integer idUsuario) {
        Usuario usuario = usuarioRepository
                    .findById(idUsuario)
                    .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        repository
        .findById(id)
        .map( tarefa -> {
            tarefa.setUsuario(usuario);
            return repository.save(tarefa);
        }).orElseThrow(() -> new AppException("Unknown task", HttpStatus.NOT_FOUND) );
    }

    @Override
    public List<Tarefa> consultarTarefasUsuario(Integer id) {
        return repository.consultarTarefasUsuario(id);
    }


   
    
}

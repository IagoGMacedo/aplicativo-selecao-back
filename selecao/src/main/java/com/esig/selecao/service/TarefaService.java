package com.esig.selecao.service;
    
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;

import com.esig.selecao.model.Tarefa;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.rest.dto.TarefaDTO;


public interface TarefaService {

    public Optional<Tarefa> encontrarPeloId(Integer id);
    public Tarefa salvar(TarefaDTO tarefa);
    public Tarefa salvar(Tarefa tarefa);
    public void deletar(Tarefa tarefa);
    public List<Tarefa> encontrarTodos(Example example);
    public void atualizarUsuario(Integer id, Integer idUsuario);
    public List<Tarefa> consultarTarefasUsuario(Integer id);
    
}

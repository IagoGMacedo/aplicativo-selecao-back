package com.esig.selecao.service;
    
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.esig.selecao.model.Tarefa;
import com.esig.selecao.rest.dto.TarefaDTO;


public interface TarefaService {

    public TarefaDTO encontrarPeloId(Integer id);
    public TarefaDTO salvar(TarefaDTO tarefa);
    public TarefaDTO salvar(Tarefa tarefa);
    public void deletar(Integer id);
    public TarefaDTO update(Integer id, TarefaDTO tarefa);
    public List<TarefaDTO> encontrarTodos(Tarefa filtro);
    public List<TarefaDTO> consultarTarefasUsuario(Integer id);
    public TarefaDTO patch(Integer id, TarefaDTO tarefaIncompletaDto);
}

package com.esig.selecao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esig.selecao.model.Tarefa;


public interface TarefaRepository extends JpaRepository<Tarefa,Integer> {

    @Query("SELECT t FROM Tarefa t WHERE t.usuario.id = :usuarioId")
    public List<Tarefa> consultarTarefasUsuario(@Param("usuarioId") Integer usuarioId);
    
}

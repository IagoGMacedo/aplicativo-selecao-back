package com.esig.selecao.rest.dto;

import java.time.LocalDate;
import java.util.List;

import com.esig.selecao.enums.Prioridade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {
    private String titulo;
    private String descricao;
    private Integer usuario;
    private Prioridade prioridade; 
    private LocalDate deadLine;
}

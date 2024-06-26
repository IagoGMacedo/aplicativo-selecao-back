package com.esig.selecao.rest.dto;

import java.time.LocalDate;

import com.esig.selecao.enums.Prioridade;
import com.esig.selecao.enums.Situacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private Long usuario;
    private String nome_usuario;
    private Prioridade prioridade; 
    private Situacao situacao;
    private String deadLine;
    /* 
    private String titulo;
    private String descricao;
    private Integer usuario;
    private Prioridade prioridade; 
    private LocalDate deadLine;
    */
}


    


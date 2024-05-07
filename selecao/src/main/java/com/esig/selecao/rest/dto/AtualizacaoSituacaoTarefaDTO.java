package com.esig.selecao.rest.dto;


import com.esig.selecao.enums.Situacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizacaoSituacaoTarefaDTO {
    private Situacao novaSituacao;
}
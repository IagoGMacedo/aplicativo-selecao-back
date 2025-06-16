package com.esig.selecao.service;

import com.esig.selecao.enums.Prioridade;
import com.esig.selecao.enums.Situacao;
import com.esig.selecao.exception.AppException;
import com.esig.selecao.model.Tarefa;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.repository.TarefaRepository;
import com.esig.selecao.repository.UsuarioRepository;
import com.esig.selecao.rest.dto.TarefaDTO;
import com.esig.selecao.service.impl.TarefaServiceImpl;
import com.esig.selecao.utils.Patcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TarefaServiceTest {
    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Patcher patcher;

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void encontrarPeloId_deveRetornarTarefaDTO_quandoTarefaExiste() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1);
        tarefa.setTitulo("Teste");
        tarefa.setDescricao("Descrição");
        tarefa.setDeadLine(LocalDate.now());
        tarefa.setPrioridade(Prioridade.ALTA);
        tarefa.setSituacao(Situacao.ANDAMENTO);

        Usuario usuario = new Usuario();
        usuario.setId(10L);
        usuario.setPrimeiroNome("Usuário");
        usuario.setSobrenome("Teste");
        tarefa.setUsuario(usuario);

        when(tarefaRepository.findById(1)).thenReturn(Optional.of(tarefa));

        TarefaDTO dto = tarefaService.encontrarPeloId(1);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getTitulo()).isEqualTo("Teste");
        assertThat(dto.getUsuario()).isEqualTo(10L);
        assertThat(dto.getNome_usuario()).isEqualTo("Usuário Teste");
    }

    @Test
    void encontrarPeloId_deveLancarExcecao_quandoTarefaNaoExiste() {
        when(tarefaRepository.findById(1)).thenReturn(Optional.empty());

        AppException ex = catchThrowableOfType(() -> tarefaService.encontrarPeloId(1), AppException.class);

        assertThat(ex).isNotNull();
        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("Tarefa não encontrada");
    }

    @Test
    void salvarTarefaDTO_deveSalvarENomearCorretamente() {
        TarefaDTO dto = TarefaDTO.builder()
                .titulo("Nova tarefa")
                .descricao("Descrição")
                .usuario(10L)
                .prioridade(Prioridade.MEDIA)
                .deadLine("2024-05-07")
                .build();

        Usuario usuario = new Usuario();
        usuario.setId(10L);
        usuario.setPrimeiroNome("Usuário");
        usuario.setSobrenome("Teste");
        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(1);
        tarefaSalva.setTitulo("Nova tarefa");
        tarefaSalva.setDescricao("Descrição");
        tarefaSalva.setDeadLine(LocalDate.of(2024, 5, 7));
        tarefaSalva.setPrioridade(Prioridade.MEDIA);
        tarefaSalva.setSituacao(Situacao.ANDAMENTO);
        tarefaSalva.setUsuario(usuario);

        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);

        TarefaDTO resultado = tarefaService.salvar(dto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getTitulo()).isEqualTo("Nova tarefa");
        assertThat(resultado.getUsuario()).isEqualTo(10L);
        assertThat(resultado.getNome_usuario()).isEqualTo("Usuário Teste");
    }

    @Test
    void deletar_deveRemoverTarefa_quandoExiste() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1);

        when(tarefaRepository.findById(1)).thenReturn(Optional.of(tarefa));

        tarefaService.deletar(1);

        verify(tarefaRepository, times(1)).delete(tarefa);
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoExiste() {
        when(tarefaRepository.findById(1)).thenReturn(Optional.empty());

        AppException ex = catchThrowableOfType(() -> tarefaService.deletar(1), AppException.class);

        assertThat(ex).isNotNull();
        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

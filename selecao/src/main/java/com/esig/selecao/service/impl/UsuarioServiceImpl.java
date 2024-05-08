package com.esig.selecao.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.esig.selecao.exception.AppException;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.repository.UsuarioRepository;
import com.esig.selecao.rest.dto.UsuarioValorDTO;
import com.esig.selecao.rest.dto.authentication.UsuarioDTO;
import com.esig.selecao.service.UsuarioService;
import com.esig.selecao.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    private final Patcher patcher;

    @Override
    public UsuarioDTO encontrarPeloId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return toDto(usuario);
    }

    @Override
    public void deletar(Long id) {
        Usuario usuario = repository
                .findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        repository.delete(usuario);
    }

    @Override
    public List<UsuarioDTO> encontrarTodos(Usuario filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDtoList(repository.findAll(example));
    }

    @Override
    public UsuarioDTO patch(Long id, Usuario usuarioIncompleto) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        patcher.copiarPropriedadesNaoNulas(usuarioIncompleto, usuarioExistente);
        return toDto(repository.save(usuarioExistente));
    }

    @Override
    public UsuarioDTO update(Long id, Usuario usuario) {
        Usuario usuarioExistente = repository
                .findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        usuario.setId(usuarioExistente.getId());
        return toDto(repository.save(usuario));
    }

    @Override
    public List<UsuarioValorDTO> encontrarValores() {
        return toDtoValue(repository.findAll());
    }

    private UsuarioDTO toDto(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .primeiroNome(usuario.getPrimeiroNome())
                .sobrenome(usuario.getSobrenome())
                .login(usuario.getLogin())
                .cargo(usuario.getCargo())
                .build();
    }

    private List<UsuarioDTO> toDtoList(List<Usuario> listaUsuarios) {
        if (CollectionUtils.isEmpty(listaUsuarios)) {
            return Collections.emptyList();
        }
        return listaUsuarios.stream().map(
                usuario -> UsuarioDTO.builder()
                        .id(usuario.getId())
                        .primeiroNome(usuario.getPrimeiroNome())
                        .sobrenome(usuario.getSobrenome())
                        .login(usuario.getLogin())
                        .cargo(usuario.getCargo())
                        .build())
                .collect(Collectors.toList());
    }

    private List<UsuarioValorDTO> toDtoValue(List<Usuario> listaUsuarios){
        if (CollectionUtils.isEmpty(listaUsuarios)) {
            return Collections.emptyList();
        }
        return listaUsuarios.stream().map(
                usuario -> UsuarioValorDTO.builder()
                        .id(usuario.getId())
                        .nome(usuario.getNomeCompleto())
                        .build())
                .collect(Collectors.toList());
    }

    

}

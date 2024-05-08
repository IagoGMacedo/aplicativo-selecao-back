package com.esig.selecao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esig.selecao.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByLogin(String login);
}

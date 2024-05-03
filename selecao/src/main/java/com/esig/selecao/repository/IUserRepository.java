package com.esig.selecao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esig.selecao.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
    Optional<User> findByLogin(String login);
}

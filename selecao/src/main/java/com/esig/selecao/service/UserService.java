package com.esig.selecao.service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.esig.selecao.rest.dto.authentication.CredentialDTO;
import com.esig.selecao.rest.dto.authentication.SignUpDTO;
import com.esig.selecao.exception.AppException;
import com.esig.selecao.config.PasswordConfig;
import com.esig.selecao.enums.Role;
import com.esig.selecao.model.User;
import com.esig.selecao.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Realiza o login do usuário, pesquisa no banco de dados se o login fornecido
     * existe, se existe compara as senhas
     * 
     * @param credentialDTO Credenciais contendo login e senha
     * @return User do usuário encontrado
     */
    public User login(CredentialDTO credentialDTO) throws AppException {
        User user = userRepository.findByLogin(credentialDTO.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialDTO.password(), user.getPassword())) {
            System.out.println("deu matches");
            return user;
        }
        throw new AppException("Unknown user", HttpStatus.BAD_REQUEST);
    }

    /**
     * Realiza o cadastro do usuário, caso o login fornecido já não exista, salva um
     * novo usuário com permissões de PATIENT
     * 
     * @param signUpDTO Informações de cadastro necessárias para criar um novo
     *                  usuário
     * @return User do usuário criado
     */
    public User register(SignUpDTO signUpDTO) throws AppException {
        Optional<User> oUser = userRepository.findByLogin(signUpDTO.getLogin());

        if (oUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = extractUser(signUpDTO);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.getPassword())));
        user.setRole(Role.ADMIN);
        User savedUser = userRepository.save(user);
        System.out.println("encode ok");
        return savedUser;
    }

    private User extractUser(SignUpDTO signUpDTO) {
        User user = new User();
        user.setFirstName(signUpDTO.getFirstName());
        user.setLastName(signUpDTO.getLastName());
        user.setLogin(signUpDTO.getLogin());
        return user;
    }


}

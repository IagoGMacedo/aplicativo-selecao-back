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
import com.esig.selecao.enums.Cargo;
import com.esig.selecao.model.Usuario;
import com.esig.selecao.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    //private final IUserRepository userRepository;
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Realiza o login do usuário, pesquisa no banco de dados se o login fornecido
     * existe, se existe compara as senhas
     * 
     * @param credentialDTO Credenciais contendo login e senha
     * @return User do usuário encontrado
     */
    public Usuario login(CredentialDTO credentialDTO) throws AppException {
        Usuario user = userRepository.findByLogin(credentialDTO.login())
                .orElseThrow(() -> new AppException("credenciais não encontradas", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialDTO.senha(), user.getSenha())) {
            return user;
        }
        throw new AppException("credenciais não encontradas", HttpStatus.NOT_FOUND);
    }

    /**
     * Realiza o cadastro do usuário, caso o login fornecido já não exista, salva um
     * novo usuário com permissões de PATIENT
     * 
     * @param signUpDTO Informações de cadastro necessárias para criar um novo
     *                  usuário
     * @return User do usuário criado
     */
    public Usuario register(SignUpDTO signUpDTO) throws AppException {
        Optional<Usuario> oUser = userRepository.findByLogin(signUpDTO.getLogin());

        if (oUser.isPresent()) {
            throw new AppException("Login já existe", HttpStatus.BAD_REQUEST);
        }

        Usuario user = extractUser(signUpDTO);
        user.setSenha(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.getSenha())));
        user.setCargo(Cargo.valueOf(signUpDTO.getCargo()));
        Usuario savedUser = userRepository.save(user);
        return savedUser;
    }

    private Usuario extractUser(SignUpDTO signUpDTO) {
        Usuario user = new Usuario();
        user.setPrimeiroNome(signUpDTO.getPrimeiroNome());
        user.setSobrenome(signUpDTO.getSobrenome());
        user.setLogin(signUpDTO.getLogin());
        return user;
    }


}

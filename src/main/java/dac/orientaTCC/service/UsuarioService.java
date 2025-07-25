package dac.orientaTCC.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dac.orientaTCC.dto.UsuarioCreateDTO;
import dac.orientaTCC.exception.EmailUniqueViolationException;
import dac.orientaTCC.exception.EntityNotFoundException;
import dac.orientaTCC.mapper.UsuarioMapper;
import dac.orientaTCC.model.entities.Usuario;
import dac.orientaTCC.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = UsuarioMapper.toUsuario(usuarioCreateDTO);
        try{
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));//fazendo isso o passwordEncoder.encode realiza a criptografia da senha retornando a senha criptografada
            return usuarioRepository.save(usuario);

        }catch (DataIntegrityViolationException e){
            throw new EmailUniqueViolationException(String.format("Email {%s} já cadastrado", usuario.getEmail()));
        }
    }


    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id = '%s' não encontrado", id))
        );
    }

    @Transactional
    public Usuario editarSenha(String email, String novaSenha) {
        Usuario user = usuarioRepository.findByEmail(email).get();
        user.setSenha(passwordEncoder.encode(novaSenha));
        return user;
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String username) {
        return usuarioRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com userName = %s não encontrado", username)));
    }
}

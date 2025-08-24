package dac.orientaTCC.service;

import dac.orientaTCC.dto.*;
import dac.orientaTCC.enums.Role;
import dac.orientaTCC.exception.EntityNotFoundException;
import dac.orientaTCC.exception.NaoPodeRemoverOrientadorException;
import dac.orientaTCC.exception.SiapeUniqueViolationException;
import dac.orientaTCC.mapper.OrientadorMapper;
import dac.orientaTCC.model.entities.Orientador;
import dac.orientaTCC.model.entities.TrabalhoAcademicoTCC;
import dac.orientaTCC.model.entities.Usuario;
import dac.orientaTCC.repository.AlunoRepository;
import dac.orientaTCC.repository.OrientadorRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrientadorService {

    private final OrientadorRepository orientadorRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final TrabalhoAcademicoTCCService trabalhoAcademicoTCCService;

    public OrientadorService(
            OrientadorRepository orientadorRepository,
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder,
            @Lazy TrabalhoAcademicoTCCService trabalhoAcademicoTCCService
    ) {
        this.orientadorRepository = orientadorRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.trabalhoAcademicoTCCService = trabalhoAcademicoTCCService;
    }

    @Transactional
    public Orientador save(Orientador orientador) {
        return orientadorRepository.save(orientador);
    }

    @Transactional
    public OrientadorResponseDTO create(@Valid OrientadorCreateDTO orientadorCreateDTO) {

        Usuario usuario = usuarioService.salvar(new UsuarioCreateDTO(orientadorCreateDTO.getEmail(), orientadorCreateDTO.getSenha(), "ROLE_ORIENTADOR"));

        Orientador orientador = OrientadorMapper.toOrientador(orientadorCreateDTO);
        orientador.setUsuario(usuario);
        try{
            orientador = save(orientador);

        }catch (DataIntegrityViolationException e){
            throw new SiapeUniqueViolationException(String.format("Siape %s não pode ser cadastrado, já existente no sistema", orientadorCreateDTO.getSiape()));
        }

        return OrientadorMapper.toOrientadorDTO(orientador);
    }

    @Transactional(readOnly = true)
    public Orientador findById(Long id) {
        return orientadorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id = %s não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public Orientador findByEmail(String email) {
        return orientadorRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("orientador não encontrado"));
    }

    @Transactional(readOnly = true)
    public Orientador findBySiape(String siape) {
        return orientadorRepository.findBySiape(siape)
                .orElseThrow(() -> new EntityNotFoundException("orientador não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Orientador> findAll() {
        return orientadorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Orientador findByIdUsuario(Long id) {
        return orientadorRepository.findByUsuarioId(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id = %s não encontrado", id))
        );
    }

    @Transactional
    public Orientador update(OrientadorCreateDTO orientadorCreateDTO) {

        Orientador orientadorBuscado = findBySiape(orientadorCreateDTO.getSiape());

        if(!orientadorCreateDTO.getNome().equals(orientadorBuscado.getNome())){
            orientadorBuscado.setNome(orientadorCreateDTO.getNome());
        }

        if(!orientadorCreateDTO.getAreaAtuacao().equals(orientadorBuscado.getAreaAtuacao())){
            orientadorBuscado.setAreaAtuacao(orientadorCreateDTO.getAreaAtuacao());
        }

        if(orientadorCreateDTO.getSenha() != null){
            orientadorBuscado.getUsuario().setSenha(passwordEncoder.encode(orientadorCreateDTO.getSenha()));
        }

        return orientadorBuscado;
    }

    @Transactional
    public Orientador updateRole(OrientadorCreateDTO orientadorCreateDTO) {
        Orientador orientadorBuscado = findBySiape(orientadorCreateDTO.getSiape());

        Usuario usuario = usuarioService.buscarPorId(orientadorBuscado.getUsuario().getId());

        if (usuario.getTipoRole() == Role.ROLE_ORIENTADOR) {
            orientadorBuscado.getUsuario().setTipoRole(Role.ROLE_COORDENADOR);
        }else{
            orientadorBuscado.getUsuario().setTipoRole(Role.ROLE_ORIENTADOR);
        }
        return orientadorBuscado;
    }

    @Transactional
    public void remove(String email) {
        Orientador orientador = orientadorRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Orientador não encontrado"));

        TrabalhoAcademicoTCC trabalhoAcademico = trabalhoAcademicoTCCService.findByIdOrientador(orientador.getId());

        if (trabalhoAcademico != null) {
            throw new NaoPodeRemoverOrientadorException("Não é permitido apagar um orientador que esteja relacionado a um Trabalho Academico");
        }

        orientadorRepository.delete(orientador);
    }
}

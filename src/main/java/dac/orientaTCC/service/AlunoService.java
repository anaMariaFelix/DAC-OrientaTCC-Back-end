package dac.orientaTCC.service;

import dac.orientaTCC.dto.AlunoCreateDTO;
import dac.orientaTCC.dto.AlunoResponseDTO;
import dac.orientaTCC.dto.UsuarioCreateDTO;
import dac.orientaTCC.exception.EntityNotFoundException;
import dac.orientaTCC.exception.MatriculaUniqueViolationException;
import dac.orientaTCC.mapper.AlunoMapper;
import dac.orientaTCC.model.entities.Aluno;
import dac.orientaTCC.model.entities.TrabalhoAcademicoTCC;
import dac.orientaTCC.model.entities.Usuario;
import dac.orientaTCC.repository.AlunoRepository;
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
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final TrabalhoAcademicoTCCService trabalhoAcademicoTCCService;

    public AlunoService(
            AlunoRepository alunoRepository,
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder,
            @Lazy TrabalhoAcademicoTCCService trabalhoAcademicoTCCService
    ) {
        this.alunoRepository = alunoRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.trabalhoAcademicoTCCService = trabalhoAcademicoTCCService;
    }


    @Transactional
    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @Transactional
    public AlunoResponseDTO create(@Valid AlunoCreateDTO alunoCreateDTO) {
        Aluno aluno;
        try{
            Usuario usuario = usuarioService.salvar(new UsuarioCreateDTO(alunoCreateDTO.getEmail(), alunoCreateDTO.getSenha(), "ROLE_ALUNO"));

            aluno = AlunoMapper.toAluno(alunoCreateDTO);
            aluno.setUsuario(usuario);

            aluno = save(aluno);

        }catch (DataIntegrityViolationException e){
            throw new MatriculaUniqueViolationException(String.format("Matricula %s não pode ser cadastrada, já existente no sistema", alunoCreateDTO.getMatricula()));
        }

        return AlunoMapper.toAlunoDTO(aluno);
    }

    @Transactional(readOnly = true)
    public Aluno findById(Long id) {
        return alunoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id = %s não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public Aluno findByIdUsuario(Long id) {
        return alunoRepository.findByUsuarioId(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id = %s não encontrado", id))
        );
    }

    public Aluno findByEmail(String email) {
        return alunoRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
    }

    @Transactional(readOnly = true)
    public Aluno findByMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula).orElseThrow(
                () -> new EntityNotFoundException("Aluno não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    @Transactional
    public void remove(String email) {
        Aluno aluno = alunoRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        TrabalhoAcademicoTCC trabalhoAcademico = trabalhoAcademicoTCCService.findByIdAluno(aluno.getId());

        if (trabalhoAcademico != null) {
            trabalhoAcademicoTCCService.removeById(trabalhoAcademico.getId());
        }

        alunoRepository.delete(aluno);
    }

    @Transactional
    public Aluno update(AlunoCreateDTO alunoCreateDTO) {
        Aluno alunoBuscado = findByMatricula(alunoCreateDTO.getMatricula());

        if(!alunoCreateDTO.getNome().equals(alunoBuscado.getNome())){
            alunoBuscado.setNome(alunoCreateDTO.getNome());
        }

        if(alunoCreateDTO.getSenha() != null){
            alunoBuscado.getUsuario().setSenha(passwordEncoder.encode(alunoCreateDTO.getSenha()));
        }

        return alunoBuscado;
    }
}

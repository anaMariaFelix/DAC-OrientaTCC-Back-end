package dac.orientaTCC.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dac.orientaTCC.dto.TrabalhoAcademicoTCCCreateDTO;
import dac.orientaTCC.dto.TrabalhoAcademicoTCCResponseDTO;
import dac.orientaTCC.exception.TrabalhoAcademicoNaoEncontradoPorMatriculaException;
import dac.orientaTCC.mapper.TrabalhoAcademicoTCCMapper;
import dac.orientaTCC.model.entities.TrabalhoAcademicoTCC;
import dac.orientaTCC.repository.TrabalhoAcademicoTCCRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TrabalhoAcademicoTCCService {

    private final TrabalhoAcademicoTCCRepository trabalhoAcademicoTCCRepository;
    private final OrientadorService orientadorService;
    private final AlunoService alunoService;

    public TrabalhoAcademicoTCCService(TrabalhoAcademicoTCCRepository trabalhoAcademicoTCCRepository,
            OrientadorService orientadorService, AlunoService alunoService) {
        this.trabalhoAcademicoTCCRepository = trabalhoAcademicoTCCRepository;
        this.orientadorService = orientadorService;
        this.alunoService = alunoService;
    }

    @Transactional
    public TrabalhoAcademicoTCCResponseDTO create(TrabalhoAcademicoTCCCreateDTO dto) {
        TrabalhoAcademicoTCC entity = TrabalhoAcademicoTCCMapper.toTrabalhoAcademicoTCC(dto);
        entity.setId(null);
        entity.setVersao(null);

        entity.setOrientador(orientadorService.findBySiape(dto.getSiapeOrientador()));
        entity.setAluno(alunoService.findByMatricula(dto.getMatriculaAluno()));

        TrabalhoAcademicoTCC salvo = trabalhoAcademicoTCCRepository.save(entity);

        return TrabalhoAcademicoTCCMapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public TrabalhoAcademicoTCC findById(Long id) {
        return trabalhoAcademicoTCCRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Trabalho Academico id = %s não encontrado", id)));
    }

    @Transactional(readOnly = true)
    public TrabalhoAcademicoTCC findByMatriculaAluno(String matricula) {
        TrabalhoAcademicoTCC trabalho = trabalhoAcademicoTCCRepository.findByAlunoMatricula(matricula);

        if (trabalho == null) {
            throw new TrabalhoAcademicoNaoEncontradoPorMatriculaException("Nenhum trabalho encontrado para a matrícula: " + matricula);
        }

        return trabalho;
    }

    @Transactional(readOnly = true)
    public TrabalhoAcademicoTCC findByIdAluno(Long id) {
        TrabalhoAcademicoTCC trabalho = trabalhoAcademicoTCCRepository.findByAlunoId(id);

        return trabalho;
    }

    @Transactional(readOnly = true)
    public List<TrabalhoAcademicoTCC> findAllByOrientadorSiape(String siape) {
        return trabalhoAcademicoTCCRepository.findByOrientadorSiape(siape);
    }

    @Transactional(readOnly = true)
    public List<TrabalhoAcademicoTCC> findAll() {
        return trabalhoAcademicoTCCRepository.findAll();
    }

    @Transactional
    public void removeById(Long id) {
        TrabalhoAcademicoTCC trabalhoExistente = findById(id);
        trabalhoAcademicoTCCRepository.delete(trabalhoExistente);
    }

    public TrabalhoAcademicoTCC findByIdOrientador(Long id) {
        TrabalhoAcademicoTCC trabalho = trabalhoAcademicoTCCRepository.findByOrientadorId(id);

        return trabalho;
    }
}

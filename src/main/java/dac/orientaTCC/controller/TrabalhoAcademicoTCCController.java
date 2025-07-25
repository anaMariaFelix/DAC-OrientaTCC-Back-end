package dac.orientaTCC.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dac.orientaTCC.dto.TrabalhoAcademicoTCCCreateDTO;
import dac.orientaTCC.dto.TrabalhoAcademicoTCCResponseDTO;
import dac.orientaTCC.mapper.TrabalhoAcademicoTCCMapper;
import dac.orientaTCC.model.entities.TrabalhoAcademicoTCC;
import dac.orientaTCC.service.TrabalhoAcademicoTCCService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/trabalhoAcademico")
public class TrabalhoAcademicoTCCController {

    private final TrabalhoAcademicoTCCService trabalhoAcademicoTCCService;

    @PostMapping("/")
    //@PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<TrabalhoAcademicoTCCResponseDTO> create(@RequestBody TrabalhoAcademicoTCCCreateDTO dto) {
        TrabalhoAcademicoTCCResponseDTO response = trabalhoAcademicoTCCService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TrabalhoAcademicoTCCResponseDTO> findById(@PathVariable Long id) {
        TrabalhoAcademicoTCC response = trabalhoAcademicoTCCService.findById(id);
        return ResponseEntity.ok().body(TrabalhoAcademicoTCCMapper.toDTO(response));
    }

    @GetMapping("/matricula/{matricula}")
    //@PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<TrabalhoAcademicoTCCResponseDTO> findByMatriculaAluno(@PathVariable String matricula) {
        TrabalhoAcademicoTCC response = trabalhoAcademicoTCCService.findByMatriculaAluno(matricula);
        return ResponseEntity.ok().body(TrabalhoAcademicoTCCMapper.toDTO(response));
    }

    @GetMapping("/siape/{siape}")
    //@PreAuthorize("hasRole('ORIENTADOR')")
    public ResponseEntity<List<TrabalhoAcademicoTCCResponseDTO>> findByOrientadorSiape(@PathVariable String siape) {
        List<TrabalhoAcademicoTCC> response = trabalhoAcademicoTCCService.findAllByOrientadorSiape(siape);
        return ResponseEntity.ok().body(TrabalhoAcademicoTCCMapper.toTrabalhoAcademicoList(response));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        trabalhoAcademicoTCCService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}

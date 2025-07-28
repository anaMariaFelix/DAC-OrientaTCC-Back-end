package dac.orientaTCC.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dac.orientaTCC.dto.AlunoCreateDTO;
import dac.orientaTCC.dto.AlunoResponseDTO;
import dac.orientaTCC.mapper.AlunoMapper;
import dac.orientaTCC.model.entities.Aluno;
import dac.orientaTCC.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping("/")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<AlunoResponseDTO> create(@RequestBody @Valid AlunoCreateDTO alunoCreateDTO){
        log.info("Controller create aluno log 1");
        AlunoResponseDTO aluno = alunoService.create(alunoCreateDTO);
        log.info("Controller create aluno log 2");
        return ResponseEntity.status(HttpStatus.CREATED).body(aluno);
    }

    @PreAuthorize("hasRole('ALUNO')")
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> findById(@PathVariable Long id){
        Aluno aluno = alunoService.findById(id);
        return ResponseEntity.ok().body(AlunoMapper.toAlunoDTO(aluno));
    }

    @PreAuthorize("hasRole('COORDENADOR') OR #email == authentication.principal.username")
    @GetMapping("/email/{email}")
    public ResponseEntity<AlunoResponseDTO> findByEmail(@PathVariable String email){
        Aluno aluno = alunoService.findByEmail(email);
        return ResponseEntity.ok(AlunoMapper.toAlunoDTO(aluno));
    }

    @PreAuthorize("hasRole('COORDENADOR') OR #matricula == authentication.principal.identificador")
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<AlunoResponseDTO> findByMatricula(@PathVariable String matricula){

        Aluno aluno = alunoService.findByMatricula(matricula);
        log.info("matricula",aluno.getMatricula());
        return ResponseEntity.ok(AlunoMapper.toAlunoDTO(aluno));
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/")
    public ResponseEntity<List<AlunoResponseDTO>> findAll(){
        List<Aluno> alunos = alunoService.findAll();
        return ResponseEntity.ok(AlunoMapper.toListAlunoDTO(alunos));
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> removeByEmail(@PathVariable String email){
        alunoService.remove(email);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ALUNO') AND #alunoCreateDTO.getMatricula == authentication.principal.identificador")
    @PutMapping("/")
    public ResponseEntity<AlunoResponseDTO> update(@RequestBody AlunoCreateDTO alunoCreateDTO){
        Aluno aluno = alunoService.update(alunoCreateDTO);
        return ResponseEntity.ok().body(AlunoMapper.toAlunoDTO(aluno));
    }
}

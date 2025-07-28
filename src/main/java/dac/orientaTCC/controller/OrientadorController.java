package dac.orientaTCC.controller;

import dac.orientaTCC.dto.OrientadorCreateDTO;
import dac.orientaTCC.dto.OrientadorResponseDTO;
import dac.orientaTCC.mapper.OrientadorMapper;
import dac.orientaTCC.model.entities.Orientador;
import dac.orientaTCC.service.OrientadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/orientadores")
public class OrientadorController {

    private final OrientadorService orientadorService;

    @PreAuthorize("hasRole('COORDENADOR')")
    @PostMapping("/")
    public ResponseEntity<OrientadorResponseDTO> create(@RequestBody @Valid OrientadorCreateDTO orientadorCreateDTO){
        OrientadorResponseDTO orientadorResponseDTO = orientadorService.create(orientadorCreateDTO);
        log.info("orientador email: {}", orientadorResponseDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(orientadorResponseDTO);
    }

    @PreAuthorize("hasRole('COORDENADOR') or #id == authentication.principal.codigoUsuario")
    @GetMapping("/{id}")
    public ResponseEntity<OrientadorResponseDTO> findById(@PathVariable Long id){
        Orientador orientador = orientadorService.findById(id);
        return ResponseEntity.ok().body(OrientadorMapper.toOrientadorDTO(orientador));
    }

    @PreAuthorize("hasRole('COORDENADOR') OR #email == authentication.principal.username")
    @GetMapping("/email/{email}")
    public ResponseEntity<OrientadorResponseDTO> findByEmail(@PathVariable String email){
        Orientador orientador = orientadorService.findByEmail(email);
        return ResponseEntity.ok(OrientadorMapper.toOrientadorDTO(orientador));
    }

    @PreAuthorize("hasRole('COORDENADOR') OR #siape == authentication.principal.identificador")
    @GetMapping("/siape/{siape}")
    public ResponseEntity<OrientadorResponseDTO> findBySiape(@PathVariable String siape){
        Orientador orientador = orientadorService.findBySiape(siape);
        return ResponseEntity.ok(OrientadorMapper.toOrientadorDTO(orientador));
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/")
    public ResponseEntity<List<OrientadorResponseDTO>> findAll(){
        List<Orientador> orientadores = orientadorService.findAll();
        return ResponseEntity.ok(OrientadorMapper.toListOrientadorDTO(orientadores));
    }

    @PreAuthorize("hasRole('ORIENTADOR') AND #orientadorCreateDTO.getSiape == authentication.principal.identificador")
    @PutMapping("/")
    public ResponseEntity<OrientadorResponseDTO> update(@RequestBody OrientadorCreateDTO orientadorCreateDTO){
        Orientador orientador = orientadorService.update(orientadorCreateDTO);
        return ResponseEntity.ok().body(OrientadorMapper.toOrientadorDTO(orientador));
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @PutMapping("/siape/{siape}")
    public ResponseEntity<OrientadorResponseDTO> updateRole(@RequestBody OrientadorCreateDTO orientadorCreateDTO){
        log.info("log 1 entrou");
        Orientador orientador = orientadorService.updateRole(orientadorCreateDTO);
        log.info("log 2 passou do service");
        return ResponseEntity.ok().body(OrientadorMapper.toOrientadorDTO(orientador));
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> removeByEmail(@PathVariable String email){
        orientadorService.remove(email);
        return ResponseEntity.noContent().build();
    }
}

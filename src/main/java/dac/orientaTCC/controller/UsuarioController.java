package dac.orientaTCC.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dac.orientaTCC.dto.UsuarioResponseDTO;
import dac.orientaTCC.mapper.UsuarioMapper;
import dac.orientaTCC.model.entities.Usuario;
import dac.orientaTCC.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('COORDENADOR','ALUNO', 'ORIENTADOR')")
    public ResponseEntity<UsuarioResponseDTO> findByEmail(@PathVariable String email) {
        Usuario response = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok().body(UsuarioMapper.toUsuarioDTO(response));
    }
}

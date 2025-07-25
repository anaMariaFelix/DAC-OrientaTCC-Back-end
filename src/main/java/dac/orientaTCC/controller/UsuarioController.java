package dac.orientaTCC.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dac.orientaTCC.dto.UsuarioResponseDTO;
import dac.orientaTCC.dto.UsuarioSenhaDTO;
import dac.orientaTCC.mapper.UsuarioMapper;
import dac.orientaTCC.model.entities.Usuario;
import dac.orientaTCC.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PatchMapping("/{id}")
    //@PreAuthorize("hasAnyRole('COORDENADOR','ALUNO', 'ORIENTADOR') AND (#id == authentication.principal.id)") // hasAnyRole('COORDENADOR','ALUNO',
                                                                                                              // 'ORIENTADOR')
                                                                                                              // RECEBE
                                                                                                              // UMA
                                                                                                              // LISTA
                                                                                                              // DE
                                                                                                              // USUARIOS,
                                                                                                              // E CADA
                                                                                                              // USUARIO
                                                                                                              // SO PODE
                                                                                                              // AlTERAR
                                                                                                              // SUA
                                                                                                              // PROPRIA
                                                                                                              // SENHA
    public ResponseEntity<Void> updatePassword(@PathVariable String matriculaOuSiape, @PathVariable String novaSenha) {
        usuarioService.editarSenha(matriculaOuSiape, novaSenha);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    // @PreAuthorize("hasAnyRole('COORDENADOR','ALUNO', 'ORIENTADOR') AND (#id ==
    // authentication.principal.id)") //hasAnyRole('COORDENADOR','ALUNO',
    // 'ORIENTADOR') RECEBE UMA LISTA DE USUARIOS, E CADA USUARIO SO PODE AlTERAR
    // SUA PROPRIA SENHA
    public ResponseEntity<UsuarioResponseDTO> findByEmail(@PathVariable String email) {
        Usuario response = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok().body(UsuarioMapper.toUsuarioDTO(response));
    }
}

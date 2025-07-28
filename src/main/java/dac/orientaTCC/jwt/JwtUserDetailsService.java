package dac.orientaTCC.jwt;

import dac.orientaTCC.enums.Role;
import dac.orientaTCC.model.entities.Aluno;
import dac.orientaTCC.model.entities.Orientador;
import dac.orientaTCC.model.entities.Usuario;
import dac.orientaTCC.repository.UsuarioRepository;
import dac.orientaTCC.service.AlunoService;
import dac.orientaTCC.service.OrientadorService;
import dac.orientaTCC.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final AlunoService alunoService;
    private final OrientadorService orientadorService;

    @Override //vai buscar um usuario por email, se ele for encontrado ele é retornado no formato de um userDetails
    public UserDetails loadUserByUsername(String email){
        Usuario usuario = usuarioService.buscarPorEmail(email);
        String identificador;
        Long codigoUsuario;

        if(usuario.getTipoRole().name().equals("ROLE_ALUNO")){
            Aluno aluno = alunoService.findByIdUsuario(usuario.getId());
            identificador = aluno.getMatricula();
            codigoUsuario = aluno.getId();
        }else {
            Orientador orientador = orientadorService.findByIdUsuario(usuario.getId());
            identificador = orientador.getSiape();
            codigoUsuario = orientador.getId();
        }

        log.info("Usuário encontrado: {}, senha no banco: {}", usuario.getEmail(), usuario.getSenha());
        return new JwtUserDetails(usuario, identificador, codigoUsuario);
    }

    //utilizado para gerar o token jwt, quando o cliente vai autenticar na aplicação
    public JwtToken getTokenAuthenticated(String email){

        Role role = usuarioService.buscarPorEmail(email).getTipoRole();
        return JwtUtils.createToken(email, role.name());
    }
}

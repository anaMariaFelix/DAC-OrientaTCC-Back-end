package dac.orientaTCC.controller;

import dac.orientaTCC.controller.exception.ErrorMessage;
import dac.orientaTCC.dto.UsuarioLoginDTO;
import dac.orientaTCC.jwt.JwtToken;
import dac.orientaTCC.jwt.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class AutenticacaoController {

    private final JwtUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDTO dto, HttpServletRequest request){

        log.info("Processo de autenticação pelo login {}",dto.getEmail());

        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
            log.info("2 log");
            authenticationManager.authenticate(authenticationToken);
            log.info("3 log");
            JwtToken token = userDetailsService.getTokenAuthenticated(dto.getEmail());
            log.info("4 log");
            return ResponseEntity.ok(token);

        }catch(AuthenticationException e){
            log.error("Bad Credentials from userName '{}'",dto.getEmail());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,"Credenciais invalidas"));
    }
}

package dac.orientaTCC.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override  //lança a exceção quando o usuario n estiver logado com a resposta 401
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Http status 401 {}", authException.getMessage());

        response.setHeader("www-authenticate", "Bearer realm='/api/auth'");//quando o usuario não estiver autenticado vai aparecer no cabeçario essas informações e essa parte do bearer informa que ele deve enviar um token para esse caminho
        response.sendError(401);
    }
}

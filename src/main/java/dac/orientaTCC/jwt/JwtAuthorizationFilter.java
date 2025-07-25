package dac.orientaTCC.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Override  //metodo que intercepta as requisições, herdado da classe extendida
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION); //recebe uma string como parametro que sera a string do cabeçario

        if(token == null || !token.startsWith(JwtUtils.JWT_BEARER)){ //startsWith testa a parte inicial de uma string
            log.info("JWT Token esta nulo, vazio ou não iniciado com 'Bearer'.");
            filterChain.doFilter(request,response);
            //as requisções que não precisam de um token ficam po esse if, como por exemplo as de criação de um usuario ou de autenticação na aplicaçao
            return;
        }

        //caso exita um token e ele não esta valido cai nesse if
        if(!JwtUtils.isTokenValid(token)){
            log.warn("JWT Token está invalido ou expirado");
            filterChain.doFilter(request,response);
            return;
        }

        //caso exista um token e ele seja valido
        String username = JwtUtils.getEmailFromTokem(token); //pega o username do token valido

        toAuthentication(request, username);
        filterChain.doFilter(request, response); //se chegar nessa linha significa que o spring finalizou a parte de authenticação pelo token e assim o usuario vai conseguir acessar o metodo que ele esta tentando acessar com a requição solicitada
    }

    private void toAuthentication(HttpServletRequest request, String email) {

        //pega o email para fazer a consulta pelo o usuario no banco de dados
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        //caso exista um usuario e de tudo certo
        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // a passagem do request que é o objeto de requisição,
                                                                                                    // para a parte de authenticação do spring security
                                                                                                    // e assim ele consegue unir as operações que é a parte de segurança com as operações da requisição
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); //o authenticationToken com todas as informações necessarias para realizar a authenticação desse usuario
    }
}

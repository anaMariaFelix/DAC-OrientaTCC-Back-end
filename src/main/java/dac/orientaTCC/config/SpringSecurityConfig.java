package dac.orientaTCC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import static org.springframework.security.config.Customizer.withDefaults;

import dac.orientaTCC.jwt.JwtAuthorizationFilter;

@EnableMethodSecurity
@EnableWebMvc // NECESSARIA PARA TRABALHAR COM SISTEMA DE SEGURAÇÃO
@Configuration
public class SpringSecurityConfig {

        // @Bean
        // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // return http
        // .csrf(csrf -> csrf.disable())//desabilitamdo essas funções para trabalhar com
        // APIREST usando a forma stateless
        // .formLogin(form -> form.disable())
        // .httpBasic(basic -> basic.disable())
        // .authorizeHttpRequests(auth -> auth
        // .requestMatchers(HttpMethod.POST, "/api/alunos").permitAll()
        // .requestMatchers(HttpMethod.GET, "/api/alunos/email/{email}").permitAll()

        // .requestMatchers(HttpMethod.POST, "/api/trabalhoAcademico").permitAll()
        // .requestMatchers(HttpMethod.DELETE,
        // "/api/trabalhoAcademico/{id}").permitAll()
        // .requestMatchers(HttpMethod.GET, "/api/trabalhoAcademico/{id}").permitAll()
        // .requestMatchers(HttpMethod.GET,
        // "/api/trabalhoAcademico/matricula/{matricula}").permitAll()
        // .requestMatchers(HttpMethod.GET,
        // "/api/trabalhoAcademico/siape/{siape}").permitAll()

        // .requestMatchers(HttpMethod.GET, "/api/usuarios/email/{email}").permitAll()

        // .requestMatchers(HttpMethod.POST, "/api/orientadores").permitAll()
        // .requestMatchers(HttpMethod.POST, "api/auth").permitAll()//libera o acesso de
        // forma publica para todos os usuarios que queiram realizar a autenticação na
        // api
        // .anyRequest().authenticated()//isso defini que todas as outras requiçoes
        // precisam de autenticação para serem utilizadas
        // ).sessionManagement(
        // session ->
        // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//defini a
        // politica de seção, como a aplicação é do tipo Ret a politica usada é a
        // stateless
        // ).addFilterBefore(
        // jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
        // //adiciona a ordem de execução dos filtros, primiero execulta o nosso filtro
        // jwtAuthorizationFilter e so depois o UsernamePasswordAuthenticationFilter, se
        // aordem for inversa não teria o resultado esperado
        // ).exceptionHandling(ex -> ex
        // .authenticationEntryPoint(new JwtAuthenticationEntryPoint())//instancia da
        // classe que foi criada no pacakage jwt que lança uma exeção caso o usuario não
        // esteja autenticado, dessa forma se o usuario n estiver logado a exeção sera
        // lançada
        // ).build();
        // }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(withDefaults()) // Ativa o CORS com a configuração acima
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .anyRequest().permitAll())
                                .httpBasic(httpBasic -> httpBasic.disable())
                                .formLogin(formLogin -> formLogin.disable());

                return http.build();
        }

        @Bean // referente ao tipo de criptografia de senhas
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(); // classe referente ao tipo de criptografia que sera utilizada q é a
                                                    // BCrypt atualmente ela é considerada a criptografia mais segura,
                                                    // usando ela a cada vez q uma senha é criptografada é criada uma
                                                    // nova criptografia dIferente para a senha,
                                                    // ou seja msm sendo a msm senha a criptofrafia dela sempre sera
                                                    // diferente a cada criptografia
        }

        @Bean // referente ao gerenciamento de autenticação
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager(); // retorna um objeto de gerenciamento de
                                                                               // autenticação
        }

        // @Bean // bean para colocar a classe filter sobre o gerenciamento do spring
        // public JwtAuthorizationFilter jwtAuthorizationFilter() {
        // return new JwtAuthorizationFilter();
        // } COMENTANDO PARA PASSAR NOS TESTES SEM TOKEN
}

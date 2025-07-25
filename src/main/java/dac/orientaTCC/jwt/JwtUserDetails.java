package dac.orientaTCC.jwt;

import dac.orientaTCC.model.entities.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private Long id;
    private Long codigoUsuario;
    private String identificador;
    private Usuario usuario;

    public JwtUserDetails(Usuario usuario, String identificador, Long codigoUsuario){
        super(usuario.getEmail(), usuario.getSenha(), AuthorityUtils.createAuthorityList(usuario.getTipoRole().name()));
        this.identificador = identificador;
        this.id = usuario.getId();
        this.codigoUsuario = codigoUsuario;
    }

    //so precisa pegar o id e a role, pq como a classe extends da User ela ja tras o getEmail e o getSenha.
    public Long getId(){
        return this.id;
    }

    public String getRole(){
        return this.usuario.getTipoRole().name();
    }

    public String getIdentificador(){
        return this.identificador;
    }

    public Long getCodigoUsuario(){
        return this.codigoUsuario;
    }

}

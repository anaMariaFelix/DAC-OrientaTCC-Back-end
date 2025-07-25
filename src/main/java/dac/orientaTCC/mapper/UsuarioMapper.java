package dac.orientaTCC.mapper;

import dac.orientaTCC.dto.UsuarioCreateDTO;
import dac.orientaTCC.dto.UsuarioResponseDTO;
import dac.orientaTCC.model.entities.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDTO usuarioDto){
        return new ModelMapper().map(usuarioDto, Usuario.class);
    }

    public static UsuarioResponseDTO toUsuarioDTO(Usuario usuario){
        String role = usuario.getTipoRole().name().substring("ROLE_".length());

        PropertyMap<Usuario, UsuarioResponseDTO> props = new PropertyMap<Usuario, UsuarioResponseDTO>() {
            @Override
            protected void configure() {
                map().setTipoRole(role);
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    public static List<UsuarioResponseDTO> toListUsuariosDTO(List<Usuario> usuarios){

        return usuarios.stream().map(u -> toUsuarioDTO(u)).collect(Collectors.toList());
    }
}

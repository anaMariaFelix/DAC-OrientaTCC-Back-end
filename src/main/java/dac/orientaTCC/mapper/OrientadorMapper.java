package dac.orientaTCC.mapper;

import dac.orientaTCC.dto.OrientadorCreateDTO;
import dac.orientaTCC.dto.OrientadorResponseDTO;
import dac.orientaTCC.model.entities.Orientador;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrientadorMapper {

    public static Orientador toOrientador(OrientadorCreateDTO orientadorCreateDTO){
        return new ModelMapper().map(orientadorCreateDTO, Orientador.class);
    }

    public static OrientadorResponseDTO toOrientadorDTO(Orientador orientador){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Orientador.class, OrientadorResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsuario().getEmail(), OrientadorResponseDTO::setEmail);
        });

        return modelMapper.map(orientador, OrientadorResponseDTO.class);
    }

    public static List<OrientadorResponseDTO> toListOrientadorDTO(List<Orientador> orientadores){
        return orientadores.stream().map(o -> toOrientadorDTO(o)).collect(Collectors.toList());
    }
}

package dac.orientaTCC.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import dac.orientaTCC.dto.TrabalhoAcademicoTCCCreateDTO;
import dac.orientaTCC.dto.TrabalhoAcademicoTCCResponseDTO;
import dac.orientaTCC.model.entities.TrabalhoAcademicoTCC;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrabalhoAcademicoTCCMapper {

    public static TrabalhoAcademicoTCCResponseDTO toDTO(TrabalhoAcademicoTCC entity) {
        return new ModelMapper().map(entity, TrabalhoAcademicoTCCResponseDTO.class);
    }

    public static TrabalhoAcademicoTCC toTrabalhoAcademicoTCC(TrabalhoAcademicoTCCCreateDTO create) {
        return new ModelMapper().map(create, TrabalhoAcademicoTCC.class);
    }

    public static List<TrabalhoAcademicoTCCResponseDTO> toTrabalhoAcademicoList(List<TrabalhoAcademicoTCC> list){
        return list.stream().map(trabalho -> toDTO(trabalho)).collect(Collectors.toList());
    }

}

package dac.orientaTCC.mapper;

import org.modelmapper.ModelMapper;
import dac.orientaTCC.dto.AtividadeDTO;
import dac.orientaTCC.model.entities.Atividade;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AtividadeMapper {

    public static Atividade toAtividade(AtividadeDTO atividadeDTO){
        return new ModelMapper().map(atividadeDTO, Atividade.class);
    }
}

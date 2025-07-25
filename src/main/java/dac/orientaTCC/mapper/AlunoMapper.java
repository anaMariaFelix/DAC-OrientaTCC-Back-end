package dac.orientaTCC.mapper;

import dac.orientaTCC.dto.AlunoCreateDTO;
import dac.orientaTCC.dto.AlunoResponseDTO;
import dac.orientaTCC.model.entities.Aluno;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlunoMapper {

    public static Aluno toAluno(AlunoCreateDTO alunoCreateDTO){
        return new ModelMapper().map(alunoCreateDTO, Aluno.class);
    }

    public static AlunoResponseDTO toAlunoDTO(Aluno aluno){
        AlunoResponseDTO dto = new ModelMapper().map(aluno, AlunoResponseDTO.class);
        dto.setEmail(aluno.getUsuario().getEmail());
        return dto;
    }

    public static List<AlunoResponseDTO> toListAlunoDTO(List<Aluno> alunos){
        return alunos.stream().map(a -> toAlunoDTO(a)).collect(Collectors.toList());
    }
}

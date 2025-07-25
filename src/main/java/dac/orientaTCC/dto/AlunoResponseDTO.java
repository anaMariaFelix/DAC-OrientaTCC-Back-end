package dac.orientaTCC.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class AlunoResponseDTO {

    private String matricula;
    private String nome;
    private String email;
}

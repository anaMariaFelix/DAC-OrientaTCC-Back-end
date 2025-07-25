package dac.orientaTCC.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrientadorResponseDTO {

    private String siape;
    private String nome;
    private String email;
    private String areaAtuacao;
}

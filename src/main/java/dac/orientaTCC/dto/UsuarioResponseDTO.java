package dac.orientaTCC.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioResponseDTO {

    private String email;
    private String senha;
    private String tipoRole;
}

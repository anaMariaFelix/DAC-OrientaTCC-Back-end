package dac.orientaTCC.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDTO {

    @NotBlank
    @Size(min = 4)
    private String senhaAtual;

    @NotBlank
    @Size(min = 4)
    private String novaSenha;

    @NotBlank
    @Size(min = 4)
    private String confirmaSenha;
}

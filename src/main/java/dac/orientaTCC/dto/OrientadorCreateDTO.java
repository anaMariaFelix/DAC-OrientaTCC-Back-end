package dac.orientaTCC.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrientadorCreateDTO {

    @NotBlank(message = "O SIAPE do orientador deve ser informado")
    @Size(min = 7, max = 7)
    private String siape;

    @NotBlank(message = "A nome do orientador deve ser informado")
    private String nome;

    @NotBlank(message = "A área de atuação do orientador deve ser informado")
    private String areaAtuacao;

    @NotBlank(message = "Um email válido deve ser informado")
    @Email(message = "Formato de email invalido!", regexp = "^[a-z0-9.+-]+@[a-z0-9.+-]+\\.[a-z]{2,}$")
    private String email;

    @NotBlank(message = "A senha do orientador deve ser informada contendo no minimo 6 caracteres")
    @Size(min = 6)
    private String senha;
}

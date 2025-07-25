package dac.orientaTCC.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlunoCreateDTO {

    @NotBlank(message = "A matricula do aluno deve ser informada")
    @Size(min = 12, max = 12)
    private String matricula;

    @NotBlank(message = "A nome do aluno deve ser informado")
    private String nome;

    @NotBlank(message = "Um email válido deve ser informado")
    @Email(message = "Formato de email invalido!", regexp = "^[a-z0-9.+-]+@[a-z0-9.+-]+\\.[a-z]{2,}$")
    private String email;

    @NotBlank(message = "A senha do aluno deve ser informada contendo no minimo 6 caracteres")
    @Size(min = 6)
    private String senha;
}

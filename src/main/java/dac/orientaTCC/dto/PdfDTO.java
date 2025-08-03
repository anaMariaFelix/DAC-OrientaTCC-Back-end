package dac.orientaTCC.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PdfDTO {
	
	private Long id;
	
	@NotBlank(message = "O nome do arquivo deve ser informado")
    private String nomeArquivo;

    private byte[] conteudo;

    private Long idAtividade;

    @NotBlank(message = "O nome de quem adicionou o arquivo deve ser informado")
    private String nomeAdicionou;
	
	
}

package dac.orientaTCC.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import dac.orientaTCC.enums.StatusPDF;
import dac.orientaTCC.model.entities.TrabalhoAcademicoTCC;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Validated
public class AtividadeDTO {

	private Long id;

	@NotNull(message = "A data de entrega deve ser informada")
	@FutureOrPresent(message = "A data informada deve ser hoje ou no futuro")
	private LocalDate dataEntrega;

	@NotBlank(message = "O nome da atividade deve ser informada")
	private String nome;

	@NotNull(message = "O status da entrega deve ser informado")
	private StatusPDF statusPdf;

	@NotBlank(message = "A descrição da atividade deve ser informada")
	private String descricao;

	private Long idTrabalhoAcademico;

	private List<PdfDTO> pdfs;

	private String comentarios;
}

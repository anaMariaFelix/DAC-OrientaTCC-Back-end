package dac.orientaTCC.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import dac.orientaTCC.enums.StatusTrabalho;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class TrabalhoAcademicoTCCResponseDTO {
    
    private Long id;
    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataInicio;

    private String nomeOrientador;
    private String nomeAluno;
    private StatusTrabalho status;
    private List<AtividadeDTO> atividades;
    
}

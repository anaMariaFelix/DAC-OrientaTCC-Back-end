package dac.orientaTCC.mapper;

import java.util.List;
import java.util.stream.Collectors;

import dac.orientaTCC.dto.AtividadeDTO;
import dac.orientaTCC.dto.PdfDTO;
import dac.orientaTCC.model.entities.Atividade;
import dac.orientaTCC.model.entities.PDF;

public class AtividadeMapper {
	
	public static Atividade atividadeMapper(AtividadeDTO atividadeDTO) {
		Atividade atividade = new Atividade();
		
		atividade.setComentario(atividadeDTO.getComentarios());
		atividade.setDataEntrega(atividadeDTO.getDataEntrega());
		atividade.setDescricao(atividadeDTO.getDescricao());
		atividade.setNome(atividadeDTO.getNome());
		atividade.setStatus(atividadeDTO.getStatusPdf());
		//atividade.set(atividadeDTO.getTrabalhoAcademico());
		
		// Aqui faz a conversão de PdfDTO para PDF (se a lista não for nula)
        if (atividadeDTO.getPdfs() != null) {
            List<PDF> pdfs = atividadeDTO.getPdfs().stream()
                .map(AtividadeMapper::pdfDtoToEntity)
                .collect(Collectors.toList());

            atividade.setPdfs(pdfs);
        }

		
		return atividade;
	}
	
	// Conversão de PdfDTO para PDF
    public static PDF pdfDtoToEntity(PdfDTO pdfDTO) {
        if (pdfDTO == null) return null;

        PDF pdf = new PDF();
        pdf.setNomeArquivo(pdfDTO.getNomeArquivo());
        pdf.setConteudo(pdfDTO.getConteudo());

        return pdf;
    }
}

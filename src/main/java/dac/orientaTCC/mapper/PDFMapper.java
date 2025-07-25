package dac.orientaTCC.mapper;

import dac.orientaTCC.dto.PdfDTO;
import dac.orientaTCC.model.entities.Atividade;
import dac.orientaTCC.model.entities.PDF;

public class PDFMapper {
	
	public static PDF mappearPdf(PdfDTO pdfDto, Atividade atividade) {
		PDF pdf = new PDF();
		
		pdf.setAtividade(atividade);
		pdf.setConteudo(pdfDto.getConteudo());
		pdf.setNomeArquivo(pdfDto.getNomeArquivo());
		
		
		return pdf;
		
	}

}

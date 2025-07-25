package dac.orientaTCC.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dac.orientaTCC.dto.PdfDTO;
import dac.orientaTCC.mapper.PDFMapper;
import dac.orientaTCC.model.entities.Atividade;
import dac.orientaTCC.model.entities.PDF;
import dac.orientaTCC.repository.AtividadeRepository;
import dac.orientaTCC.repository.PDFRepository;

@Service
public class PDFService {
	
	@Autowired
	private PDFRepository pdfRepository;

	@Autowired
	private AtividadeRepository atividadeRepository;

	/*public PDF salvarPdf(PdfDTO pdfDTO) {
	    Optional<Atividade> atividadeOptional = atividadeRepository.findById(pdfDTO.getIdAtividade());

	    if (atividadeOptional.isEmpty()) {
	        throw new RuntimeException("Atividade não encontrada.");
	    }

	    Atividade atividade = atividadeOptional.get();
	    PDF pdf = PDFMapper.mappearPdf(pdfDTO, atividade);

	    return pdfRepository.save(pdf);
	}*/
	
	public PDF salvarPdf(PdfDTO pdfDTO) {
	    Optional<Atividade> atividadeOptional = atividadeRepository.findById(pdfDTO.getIdAtividade());

	    if (atividadeOptional.isEmpty()) {
	        throw new RuntimeException("Atividade não encontrada.");
	    }

	    Atividade atividade = atividadeOptional.get();
	    PDF pdf = PDFMapper.mappearPdf(pdfDTO, atividade);

	    // 💥 Corrige aqui: certifique-se que o conteúdo seja atribuído
	    pdf.setConteudo(pdfDTO.getConteudo());

	    return pdfRepository.save(pdf);
	}
	
	// Buscar PDF por ID e retornar DTO
	public Optional<PdfDTO> buscarPdfPorId(Long id) {
	    return pdfRepository.findById(id)
	        .map(pdf -> {
	            PdfDTO dto = new PdfDTO();
	            dto.setId(pdf.getId());
	            dto.setNomeArquivo(pdf.getNomeArquivo());
	            dto.setConteudo(pdf.getConteudo()); // 💥 ESSENCIAL
	            dto.setIdAtividade(pdf.getAtividade().getId());
	            return dto;
	        });
	}

    public boolean deletarPdf(Long id) {
        if (pdfRepository.existsById(id)) {
            pdfRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

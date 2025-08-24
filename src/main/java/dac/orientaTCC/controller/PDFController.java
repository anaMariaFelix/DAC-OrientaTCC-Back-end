package dac.orientaTCC.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dac.orientaTCC.dto.PdfDTO;
import dac.orientaTCC.model.entities.PDF;
import dac.orientaTCC.service.PDFService;

@RestController
@RequestMapping("/pdf")
public class PDFController {

	@Autowired
	private PDFService pdfService;

	@PreAuthorize("hasAnyRole('ALUNO', 'ORIENTADOR', 'COORDENADOR')")
	@PostMapping("/salvar")
	public ResponseEntity<?> salvarPdf(
			@RequestParam("file") MultipartFile file,
			@RequestParam("atividadeId") Long atividadeId) {
		try {
			PdfDTO pdfDTO = new PdfDTO();
			pdfDTO.setNomeArquivo(file.getOriginalFilename());
			pdfDTO.setConteudo(file.getBytes());
			pdfDTO.setIdAtividade(atividadeId);

			PDF pdfSalvo = pdfService.salvarPdf(pdfDTO);

			return ResponseEntity.status(HttpStatus.CREATED).body(pdfSalvo);

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<PdfDTO> buscarPdfPorId(@PathVariable Long id) {
		return ResponseEntity.ok().body(pdfService.buscarPdfPorId(id).get());
	}

	@GetMapping(value = "/arquivo/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
		Optional<PdfDTO> pdfOpt = pdfService.buscarPdfPorId(id);
		if (pdfOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		PdfDTO pdf = pdfOpt.get();

		byte[] conteudoPdf = pdf.getConteudo();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + pdf.getNomeArquivo() + "\"")
				.contentType(MediaType.APPLICATION_PDF)
				.body(conteudoPdf);
	}

	@PreAuthorize("hasAnyRole('ALUNO', 'ORIENTADOR', 'COORDENADOR')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPdf(@PathVariable Long id) {
		boolean deletado = pdfService.deletarPdf(id);
		if (deletado) {
			return ResponseEntity.noContent().build(); // 204 No Content
		} else {
			return ResponseEntity.notFound().build();  // 404 Not Found
		}
	}
}
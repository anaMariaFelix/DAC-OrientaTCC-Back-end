package dac.orientaTCC.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dac.orientaTCC.model.entities.TrabalhoAcademicoTCC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dac.orientaTCC.dto.AtividadeDTO;
import dac.orientaTCC.dto.PdfDTO;
import dac.orientaTCC.mapper.AtividadeMapper;
import dac.orientaTCC.model.entities.Atividade;
import dac.orientaTCC.model.entities.PDF;
import dac.orientaTCC.repository.AtividadeRepository;

@Service
public class AtividadeService {

	private final AtividadeRepository atividadeRepository;
	private final TrabalhoAcademicoTCCService trabalhoAcademicoTCCService;

	public AtividadeService(AtividadeRepository atividadeRepository, TrabalhoAcademicoTCCService trabalhoAcademicoTCCService){
		this.atividadeRepository = atividadeRepository;
		this.trabalhoAcademicoTCCService = trabalhoAcademicoTCCService;
	}

	public ResponseEntity<?> salvarAtividade(AtividadeDTO atividadeDTO, List<MultipartFile> arquivos) {
		try {
			List<PdfDTO> pdfDTOs = new ArrayList<>();

			if (arquivos != null && !arquivos.isEmpty()) {
				for (MultipartFile arquivo : arquivos) {
					PdfDTO pdfDto = new PdfDTO();
					pdfDto.setNomeArquivo(arquivo.getOriginalFilename());
					pdfDto.setConteudo(arquivo.getBytes());
					pdfDto.setNomeAdicionou(atividadeDTO.getNomeAdicionouPdfs());

					pdfDTOs.add(pdfDto);
				}
			}

			atividadeDTO.setPdfs(pdfDTOs);

			Atividade atividade = AtividadeMapper.toAtividade(atividadeDTO);
			
			if (atividade.getPdfs() != null) {
				for (PDF pdf : atividade.getPdfs()) {
					pdf.setAtividade(atividade);
				}
			}

			Atividade atividadeSalva = atividadeRepository.save(atividade);

			return ResponseEntity.status(HttpStatus.CREATED).body(atividadeSalva);

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar os arquivos.");
		}
	}

	public ResponseEntity<?> editarAtividade(AtividadeDTO atividadeDTO, List<MultipartFile> arquivos,
			String tipoUser) {

		if (atividadeDTO.getId() == null) {
			return ResponseEntity.badRequest().body("ID da atividade não pode ser nulo");
		}

		Optional<Atividade> optional = atividadeRepository.findById(atividadeDTO.getId());
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada");
		}

		Optional<Atividade> atividadeOptional = atividadeRepository.findById(atividadeDTO.getId());
		if (atividadeOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada.");
		}

		Atividade atividadeExistente = atividadeOptional.get();
		TrabalhoAcademicoTCC trabalhoExistente = trabalhoAcademicoTCCService.findById(atividadeDTO.getIdTrabalho());//ADICIONEI esse objeto aqui

		atividadeExistente.setNome(atividadeDTO.getNome());
		atividadeExistente.setDescricao(atividadeDTO.getDescricao());
		atividadeExistente.setComentarios(atividadeDTO.getComentarios());
		atividadeExistente.setDataEntrega(atividadeDTO.getDataEntrega());
		atividadeExistente.setStatus(atividadeDTO.getStatusPdf());
		atividadeExistente.setTrabalho(trabalhoExistente);

		// Adiciona novos PDFs 
		if (arquivos != null && !arquivos.isEmpty()) {
			for (MultipartFile arquivo : arquivos) {
				PDF novoPdf = new PDF();
				novoPdf.setNomeAdicionou(atividadeDTO.getNomeAdicionouPdfs());
				novoPdf.setNomeArquivo(arquivo.getOriginalFilename());
				try {
					novoPdf.setConteudo(arquivo.getBytes());
				} catch (IOException e) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("Erro ao processar arquivo " + arquivo.getOriginalFilename());
				}
				novoPdf.setAtividade(atividadeExistente);

				atividadeExistente.getPdfs().add(novoPdf);
			}
		}

		Atividade atividadeAtualizada = atividadeRepository.save(atividadeExistente);
		return ResponseEntity.ok(atividadeAtualizada);
	}

	public List<Atividade> listarTodos() {
		return atividadeRepository.findAll();
	}

	public ResponseEntity<?> listaAtividade(Long id) {
		Optional<Atividade> atividadeOptional = atividadeRepository.findById(id);

		if (atividadeOptional.isPresent()) {
			return ResponseEntity.ok(atividadeOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada");
		}
	}

	public List<Atividade> listarPorTrabalho(Long id) {
		return atividadeRepository.findByTrabalhoId(id);
	}

	public ResponseEntity<?> deletar(Long id) {
		Optional<Atividade> atividadeOptional = atividadeRepository.findById(id);

		if (atividadeOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Atividade nao encontrada");
		} else {
			atividadeRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

	}

}

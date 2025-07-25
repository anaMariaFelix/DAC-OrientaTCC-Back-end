package dac.orientaTCC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dac.orientaTCC.dto.AtividadeDTO;
import dac.orientaTCC.model.entities.Atividade;
import dac.orientaTCC.service.AtividadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/atividade")
public class AtividadeController {

	@Autowired
	private AtividadeService atividadeService;


	@PostMapping("/salvarAtividade")
	public ResponseEntity<?> salvarAtividade(@Valid @RequestPart("atividade") AtividadeDTO atividadeDTO,
			@RequestPart(value = "arquivos", required = false) List<MultipartFile> arquivos) {
		return atividadeService.salvarAtividade(atividadeDTO, arquivos);
	}

	@PutMapping(value = "/editar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> editarAtividade(
		@PathVariable Long id,
	    @RequestPart("atividade") @Valid AtividadeDTO atDTO,
	    @RequestPart(value = "arquivos", required = false) List<MultipartFile> arquivos,
	    @RequestHeader("tipoUser") String tipoUser) {
		
		atDTO.setId(id);

	    return atividadeService.editarAtividade(atDTO, arquivos, tipoUser);
	}
	
	@GetMapping("/listarTodos")
	public List<Atividade> listarTodos() {
		return atividadeService.listarTodos();
	}

	@GetMapping("/listar/{id}")
	public ResponseEntity<?> listarAtividade(@PathVariable Long id) {
		return atividadeService.listaAtividade(id);

	}
	
	@GetMapping("/atividades/trabalho/{trabalhoId}")
	public List<Atividade> listarAtividadesPorTrabalho(@PathVariable Long trabalhoId) {
	    return atividadeService.listarPorTrabalho(trabalhoId);
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		return atividadeService.deletar(id);
	}

}

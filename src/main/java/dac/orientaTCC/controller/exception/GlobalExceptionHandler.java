package dac.orientaTCC.controller.exception;

import java.util.HashMap;
import java.util.Map;

import dac.orientaTCC.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.BAD_REQUEST,
                "Erro de validação nos campos.",
                ex.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();

        String mensagem = "Entrada duplicada no banco de dados.";

        if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("Duplicate entry")) {
            mensagem = "Usuário com identificador já existe no banco.";
        } else {
            mensagem = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        }
        response.put("message", mensagem);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TrabalhoAcademicoNaoEncontradoPorMatriculaException.class)
    public ResponseEntity<ErrorMessage> handleTrabalhoNaoEncontrado(
            TrabalhoAcademicoNaoEncontradoPorMatriculaException ex, HttpServletRequest request) {
        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.NOT_FOUND,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException e, HttpServletRequest request){

        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler({SiapeUniqueViolationException.class, MatriculaUniqueViolationException.class})
    public ResponseEntity<ErrorMessage> uniqueViolationException(RuntimeException e, HttpServletRequest request){

        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler({EmailUniqueViolationException.class})
    public ResponseEntity<ErrorMessage> emailUniqueViolationException(RuntimeException e, HttpServletRequest request){

        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage()));
    }
}

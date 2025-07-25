package dac.orientaTCC.controller.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dac.orientaTCC.exception.TrabalhoAcademicoNaoEncontradoPorMatriculaException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura exceções de validação creates
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

    // excecao personalizada
    @ExceptionHandler(TrabalhoAcademicoNaoEncontradoPorMatriculaException.class)
    public ResponseEntity<ErrorMessage> handleTrabalhoNaoEncontrado(
            TrabalhoAcademicoNaoEncontradoPorMatriculaException ex, HttpServletRequest request) {
        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.NOT_FOUND,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}

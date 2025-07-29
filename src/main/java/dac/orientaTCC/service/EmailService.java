package dac.orientaTCC.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dac.orientaTCC.util.GeradorSenha;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class EmailService {

    private final String apiKey;
    private final String senderEmail;

    private final UsuarioService usuarioService;

    public EmailService(UsuarioService usuarioService) {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("BREVO_API_KEY");
        this.senderEmail = dotenv.get("BREVO_SENDER_EMAIL");
        this.usuarioService = usuarioService;
    }

    public void enviarEmail(String email, String nome) {

        String novaSenha = GeradorSenha.gerarSenhaAleatoria();

        usuarioService.editarSenha(email, novaSenha);

        String html = gerarHtmlRecuperacaoSenha(nome, novaSenha);

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> body = Map.of(
                "sender", Map.of("email", senderEmail, "name", "Orienta TCC"),
                "to", List.of(Map.of("email", email)),
                "subject", "Recuperação de senha",
                "htmlContent", html);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        rt.postForEntity("https://api.brevo.com/v3/smtp/email", entity, String.class);
    }

    private String gerarHtmlRecuperacaoSenha(String nome, String novaSenha) {
        return "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; background-color: #f8f9fa; border: 1px solid #ddd; border-radius: 8px;\">"
                +
                "<h2 style=\"color: #343a40;\">Recuperação de Senha!</h2>" +
                "<p>Olá <strong>" + nome + "</strong>,</p>" +
                "<p>Você solicitou a recuperação de senha. Aqui está sua nova senha temporária:</p>" +
                "<p style=\"font-size: 18px; font-weight: bold; color: #0d6efd;\">" + novaSenha + "</p>" +
                "<p style=\"color: #6c757d; font-size: 14px;\">Por motivos de segurança, recomendamos que você altere sua senha após o login.</p>"
                +
                "<hr style=\"margin: 20px 0;\">" +
                "<p style=\"font-size: 12px; color: #adb5bd;\">Se você não solicitou esta alteração, por favor ignore este e-mail.</p>"
                +
                "</div>";
    }
}

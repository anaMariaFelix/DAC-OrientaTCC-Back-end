package dac.orientaTCC.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dac.orientaTCC.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

//    private final EmailService emailService;
//
//    public EmailController(EmailService emailService) {
//        this.emailService = emailService;
//    }
//
//    @PostMapping("/enviar/{email}/{nome}")
//    public String send(@PathVariable String email, @PathVariable String nome) {
//        emailService.enviarEmail(email, nome);
//        return "Enviado para " + email;
//    }

}

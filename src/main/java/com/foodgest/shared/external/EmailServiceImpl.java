package com.foodgest.shared.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final boolean enabled;
    private final String from;

    public EmailServiceImpl(JavaMailSender mailSender,
                            @Value("${foodgest.mail.enabled:false}") boolean enabled,
                            @Value("${foodgest.mail.from:no-reply@foodgest.local}") String from) {
        this.mailSender = mailSender;
        this.enabled = enabled;
        this.from = from;
    }

    @Override
    public void enviarCorreoConfirmacion(String email, String nombre) {
        if (!enabled) {
            log.info("Envio de correo desactivado. Destinatario pendiente: {}", email);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Registro recibido en FoodGest");
        message.setText("""
                Hola %s,

                Recibimos tu registro en FoodGest. Tu cuenta quedo pendiente de aprobacion.
                Cuando el administrador la active, podras iniciar sesion y usar la plataforma.

                Equipo FoodGest
                """.formatted(nombre));

        mailSender.send(message);
    }
}

package servicios;

import interfaces.InterfazEnviarEmails;
import modelo.Destinatario;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ServicioEmail implements InterfazEnviarEmails {

    private Logger logger;

    public ServicioEmail(Logger logger) {
        this.logger = logger;
    }


    @Override
    public boolean enviarEmail(Destinatario dest, String email) {
        logger.info("Correo enviado a " + dest + ": " + email);
        return true;
    }
}

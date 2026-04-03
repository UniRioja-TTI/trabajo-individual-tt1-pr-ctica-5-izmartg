package com.tt1.trabajo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.Destinatario;
import org.slf4j.LoggerFactory;
import servicios.ServicioEmail;

class ServicioEmailTest {

    private static ServicioEmail servicioEmail;

    @BeforeEach
    void setUp() {
        servicioEmail = new ServicioEmail(LoggerFactory.getLogger(ServicioEmail.class));
    }

    @Test
    void testEnviarEmailRetornaTrue() {
        Destinatario dest = new Destinatario();
        String contenido = "Esto es un texto de ejemplo";

        boolean resultado = servicioEmail.enviarEmail(dest, contenido);

        assertTrue(resultado);
    }
}
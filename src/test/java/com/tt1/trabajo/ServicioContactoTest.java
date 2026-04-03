package com.tt1.trabajo;

import static org.junit.jupiter.api.Assertions.*;

import modelo.Entidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.DatosSolicitud;
import servicios.ServicioContacto;

import java.util.HashMap;
import java.util.List;

class ServicioContactoTest {

    private ServicioContacto servicio;

    @BeforeEach
    void setUp() {
        // Instanciamos la clase antes de cada test
        servicio = new ServicioContacto();
    }

    @Test
    void testGetEntitiesNoEsNuloYTieneDatos() {
        List<Entidad> entities = servicio.getEntities();

        assertNotNull(entities);
        assertFalse(entities.isEmpty());
        assertEquals(2, entities.size()); // En este caso va a haber dos entidades
    }

    @Test
    void testIsValidEntityIdConIdsValidos() {
        assertTrue(servicio.isValidEntityId(1));
        assertTrue(servicio.isValidEntityId(2));
    }

    @Test
    void testIsValidEntityIdConIdsInvalidos() {
        assertFalse(servicio.isValidEntityId(-1));
        assertFalse(servicio.isValidEntityId(3));
    }

    @Test
    void testSolicitarSimulacionGeneraTokenPositivo() {
        DatosSolicitud ds = new DatosSolicitud(new HashMap<>());

        int token = servicio.solicitarSimulation(ds);

        assertTrue(token > 0);
    }

    @Test
    void testSolicitarSimulacionEsDiferente() {
        DatosSolicitud ds = new DatosSolicitud(new HashMap<>());

        int token1 = servicio.solicitarSimulation(ds);
        int token2 = servicio.solicitarSimulation(ds);

        assertNotEquals(token1, token2);
    }
}
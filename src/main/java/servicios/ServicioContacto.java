package servicios;

import modelo.DatosSimulation;
import modelo.Entidad;
import org.springframework.stereotype.Service;
import interfaces.InterfazContactoSim;
import modelo.DatosSolicitud;
import java.util.*;

@Service
public class ServicioContacto implements InterfazContactoSim {

    private final List<Entidad> entidades = new ArrayList<>();
    private final Map<Integer, DatosSolicitud> solicitudesRecibidas = new HashMap<>();

    public ServicioContacto() {
        Entidad mono = new Entidad();
        mono.setId(1);
        mono.setDescripcion("Es un mono, no tiene mucho más.");
        mono.setName("Mono");

        Entidad monoConPistolas = new Entidad();
        monoConPistolas.setId(2);
        monoConPistolas.setDescripcion("Es un mono, PERO CON PISTOLAS.");
        monoConPistolas.setName("Mono con pistolas");

        entidades.add(mono);
        entidades.add(monoConPistolas);
    }

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        int token = (int) System.currentTimeMillis();

        while (solicitudesRecibidas.containsKey(token)){
            token++;
        }

        solicitudesRecibidas.put(token, sol);
        return token;
    }

    @Override
    public List<Entidad> getEntities() {
        return entidades;
    }

    @Override
    public boolean isValidEntityId(int id) {
        for (Entidad e : entidades) {
            if (e.getId() == id) return true;
        }
        return false;
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        return null;
    }
}
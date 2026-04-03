package servicios;

import modelo.DatosSimulation;
import modelo.Entidad;
import modelo.Punto;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.ResultadosApi;
import org.openapitools.client.api.SolicitudApi;
import org.openapitools.client.model.ResultsResponse;
import org.openapitools.client.model.Solicitud;
import org.openapitools.client.model.SolicitudResponse;
import org.springframework.stereotype.Service;
import interfaces.InterfazContactoSim;
import modelo.DatosSolicitud;
import java.util.*;

@Service
public class ServicioContacto implements InterfazContactoSim {

    private ApiClient conexion;
    private final List<Entidad> entidades = new ArrayList<>();
    private final Map<Integer, DatosSolicitud> solicitudesRecibidas = new HashMap<>();

    public ServicioContacto() {
        conexion = Configuration.getDefaultApiClient();
        conexion.setBasePath("http://localhost:8081");

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
        SolicitudApi solicitudApi = new SolicitudApi(conexion);
        Solicitud solicitud = transformarDatosSolicitudASolicitud(sol);
        SolicitudResponse resultado = null;
        int token = -1;
        try {
            resultado = solicitudApi.solicitudSolicitarPost("Izai", solicitud);
            token = resultado.getTokenSolicitud();
        } catch (ApiException e) {
            System.err.println("Exception when calling SolicitudApi#solicitudSolicitarPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

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
        ResultadosApi resultadosApi = new ResultadosApi(conexion);
        DatosSimulation datos = null;
        try {
            ResultsResponse response = resultadosApi.resultadosPost("Izai", ticket);
            datos = transformarResponseDataADatosSimulation(response);
        } catch (ApiException e) {
            System.err.println("Exception when calling ResultadosApi#resultadosPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return datos;
    }

    private DatosSimulation transformarResponseDataADatosSimulation(ResultsResponse response) {
        DatosSimulation datos = new DatosSimulation();
        Map<Integer,List<Punto>> puntos = new HashMap<>();
        String[] partes = response.getData().split("\n");
        String[] elementos;
        String linea;
        int tiempo;
        Punto punto;
        int maxSegundos = -1;

        datos.setAnchoTablero(Integer.parseInt(partes[0]));

        for(int i=1; i<partes.length; i++) {
            linea = partes[i];
            elementos = linea.split(",");
            tiempo = Integer.parseInt(elementos[0]);
            punto = new Punto();
            punto.setX(Integer.parseInt(elementos[1].trim()));
            punto.setY(Integer.parseInt(elementos[2].trim()));
            punto.setColor(elementos[3].trim());
            if(!puntos.containsKey(tiempo)) {
              puntos.put(tiempo, new ArrayList<>());
            }
            puntos.get(tiempo).add(punto);
            if (maxSegundos<tiempo) maxSegundos = tiempo;
        }
        datos.setPuntos(puntos);

        datos.setMaxSegundos(maxSegundos);

        return datos;
    }

    private Solicitud transformarDatosSolicitudASolicitud(DatosSolicitud sol) {
        Solicitud solicitud = new Solicitud();
        Map<Integer, Integer> datos = sol.getNums();
        List<String> nombresEntidades = new ArrayList<>();
        List<Integer> cantidadesEntidades = new ArrayList<>();

        for(Entidad entidad: getEntities()) {
            nombresEntidades.add(entidad.getName());
            cantidadesEntidades.add(datos.get(entidad.getId()));
        }
        solicitud.setNombreEntidades(nombresEntidades);

        solicitud.setCantidadesIniciales(cantidadesEntidades);

        return solicitud;
    }
}
package utilidades;

import org.openapitools.client.*;
import org.openapitools.client.api.*;
import org.openapitools.client.model.*;

import java.util.List;


public class EjemploSolicitud {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8081");

        SolicitudApi solicitudApi = new SolicitudApi(defaultClient);
        String nombre = "Izai";
        Solicitud solicitud = new Solicitud();
        try {
            SolicitudResponse result = solicitudApi.solicitudSolicitarPost(nombre, solicitud);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling SolicitudApi#SolicitudPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}

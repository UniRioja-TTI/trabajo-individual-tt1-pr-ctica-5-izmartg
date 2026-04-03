package utilidades;

import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.model.*;
import org.openapitools.client.api.EmailApi;

public class EjemploEmail {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        EmailApi apiInstance = new EmailApi(defaultClient);
        String emailAddress = "emailAddress_example"; // String |
        String message = "message_example"; // String |
        try {
            EmailResponse result = apiInstance.emailPost(emailAddress, message);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling EmailApi#emailPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}


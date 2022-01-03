package korolov.project.eshopweb.data;

import korolov.project.eshopweb.model.ClientDTO;
import korolov.project.eshopweb.model.ClientWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ClientClient class which has methods, that communicate with server side of application using WebFlux.
 */
@Component
public class ClientClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient clientWebClient;

    public ClientClient(@Value("${eshop_backend_url}") String backendUrl) {
        clientWebClient = WebClient.create(backendUrl + "/clients");
    }

    public Mono<ClientWebModel> create(ClientDTO client) {
        return clientWebClient.post() // HTTP POST
                .contentType(MediaType.APPLICATION_JSON) // set HTTP header
                .bodyValue(client) // POST data
                .retrieve() // request specification finished
                .bodyToMono(ClientWebModel.class); // interpret response body as one element
    }

    public Flux<ClientWebModel> readAll() {
        return clientWebClient.get() // HTTP GET
                .retrieve() // request specification finished
                .bodyToFlux(ClientWebModel.class); // interpret response body as a collection
    }

    public Mono<ClientWebModel> readById(String email) {
        return clientWebClient.get()
                .uri(ONE_URI, email).retrieve()
                .bodyToMono(ClientWebModel.class);
    }

    public Mono<ClientWebModel> update(ClientDTO client) {
        return clientWebClient.put() // HTTP PUT
                .uri(ONE_URI, client.getEmail()) // URI /{id}
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .bodyValue(client) // HTTP body
                .retrieve()
                .bodyToMono(ClientWebModel.class);
    }

    public Mono<Void> delete(String email) {
        return clientWebClient.delete() // HTTP DELETE
                .uri(ONE_URI, email) // URI
                .retrieve() // request specification finished
                .bodyToMono(Void.TYPE);
    }
}

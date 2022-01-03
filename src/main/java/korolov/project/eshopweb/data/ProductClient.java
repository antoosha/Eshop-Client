package korolov.project.eshopweb.data;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import korolov.project.eshopweb.model.ProductDTO;
import korolov.project.eshopweb.model.ProductWebModel;

/**
 * ProductClient class which has methods, that communicate with server side of application using WebFlux.
 */
@Component
public class ProductClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient productWebClient;

    public ProductClient(@Value("${eshop_backend_url}") String backendUrl) {
        productWebClient = WebClient.create(backendUrl + "/products");
    }

    public Mono<ProductWebModel> create(ProductDTO product) {
        return productWebClient.post() // HTTP POST
                .contentType(MediaType.APPLICATION_JSON) // set HTTP header
                .bodyValue(product) // POST data
                .retrieve() // request specification finished
                .bodyToMono(ProductWebModel.class); // interpret response body as one element
    }

    public Flux<ProductWebModel> readAll() {
        return productWebClient.get() // HTTP GET
                .retrieve() // request specification finished
                .bodyToFlux(ProductWebModel.class); // interpret response body as a collection
    }


    public Flux<ProductWebModel> readAllWithPriceLessEq(Double price) {
        return productWebClient.get()
                .uri("/price/less/{price}", price)
                .retrieve() // request specification finished
                .bodyToFlux(ProductWebModel.class); // interpret response body as a collection
    }

    public Mono<ProductWebModel> readById(Long id) {
        return productWebClient.get()
                .uri(ONE_URI, id).retrieve()
                .bodyToMono(ProductWebModel.class);
    }

    public Mono<ProductWebModel> update(ProductDTO product) {
        return productWebClient.put() // HTTP PUT
                .uri(ONE_URI, product.productId) // URI /{id}
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .bodyValue(product) // HTTP body
                .retrieve()
                .bodyToMono(ProductWebModel.class);
    }

    public Mono<Void> delete(Long id) {
        return productWebClient.delete() // HTTP DELETE
                .uri(ONE_URI, id) // URI
                .retrieve() // request specification finished
                .bodyToMono(Void.TYPE);
    }
}

package korolov.project.eshopweb.ui;

import korolov.project.eshopweb.data.ProductClient;
import korolov.project.eshopweb.model.ProductDTO;
import korolov.project.eshopweb.model.ProductWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/products")
public class ProductWebController {

    private final ProductClient productClient;

    public ProductWebController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productClient.readAll());
        return "products";
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam Long productId, Model model) {
        model.addAttribute("productDTO", productClient.readById(productId));
        return "productEdit";
    }

    @PostMapping("/edit")
    public String editProductSubmit(@ModelAttribute ProductDTO productDTO, Model model) {
        model.addAttribute("productDTO", productClient.update(productDTO));
        return "redirect:/products";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("productWebModel", new ProductWebModel());
        return "productAdd";
    }

    @PostMapping("/add")
    public String addProductSubmit(@ModelAttribute ProductWebModel productWebModel, Model model) {
        model.addAttribute("productWebModel",
                productClient.create(productWebModel).onErrorResume(WebClientResponseException.Conflict.class, e -> Mono.just(new ProductWebModel(true, productWebModel))));
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long productId, Model model) {
        model.addAttribute("productId", productClient.delete(productId));
        return "redirect:/products";
    }

    @GetMapping("/price/less")
    public String allWithPriceLessEq(@RequestParam Double price, Model model) {
        model.addAttribute("products", productClient.readAllWithPriceLessEq(price));
        return "products";
    }
}

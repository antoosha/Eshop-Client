package korolov.project.eshopweb.ui;

import korolov.project.eshopweb.data.ClientClient;
import korolov.project.eshopweb.model.ClientDTO;
import korolov.project.eshopweb.model.ClientWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/clients")
public class ClientWebController {

    private final ClientClient clientClient;

    public ClientWebController(ClientClient clientClient) {
        this.clientClient = clientClient;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("clients", clientClient.readAll());
        return "clients";
    }

    @GetMapping("/edit")
    public String editClient(@RequestParam String email, Model model) {
        model.addAttribute("clientDTO", clientClient.readById(email));
        return "clientEdit";
    }

    @PostMapping("/edit")
    public String editClientSubmit(@ModelAttribute ClientDTO clientDTO, Model model) {
        model.addAttribute("clientDTO", clientClient.update(clientDTO));
        return "redirect:/clients";
    }

    @GetMapping("/add")
    public String addClient(Model model) {
        model.addAttribute("clientWebModel", new ClientWebModel());
        return "clientAdd";
    }

    @PostMapping("/add")
    public String addClientSubmit(@ModelAttribute ClientWebModel clientWebModel, Model model) {
        model.addAttribute("clientWebModel",
                clientClient.create(clientWebModel).onErrorResume(WebClientResponseException.Conflict.class, e -> Mono.just(new ClientWebModel(true, clientWebModel))));
        return "clientAdd";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam String email, Model model) {
        model.addAttribute("email", clientClient.delete(email));
        return "redirect:/clients";
    }
}

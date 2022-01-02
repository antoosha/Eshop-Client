package korolov.project.eshopweb.model;

public class ClientWebModel extends ClientDTO {
    private boolean emailError;

    public ClientWebModel() {
    }

    public ClientWebModel(boolean emailError, ClientDTO clientDTO) {
        super(clientDTO.getName(), clientDTO.surname, clientDTO.getEmail());
        this.emailError = emailError;
    }

    public boolean isEmailError() {
        return emailError;
    }

    public void setEmailError(boolean emailError) {
        this.emailError = emailError;
    }
}

package korolov.project.eshopweb.model;

public class ProductWebModel extends ProductDTO{
    private boolean nameError;

    public ProductWebModel() {
    }

    public ProductWebModel(boolean nameError, ProductDTO productDTO) {
        super(productDTO.productName, productDTO.price, productDTO.productId);
        this.nameError = nameError;
    }

    public boolean isNameError() {
        return nameError;
    }

    public void setNameError(boolean nameError) {
        this.nameError = nameError;
    }
}

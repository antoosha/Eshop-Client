package korolov.project.eshopweb.model;

public class ProductWebModel extends ProductDTO{
    private boolean error;

    public ProductWebModel() {
    }

    public ProductWebModel(boolean error, ProductDTO productDTO) {
        super(productDTO.productName, productDTO.price, productDTO.productId);
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

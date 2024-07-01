package gift.main.dto;

import gift.main.entity.Product;

import java.util.List;

public class ListProductResponse extends Response {
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public ListProductResponse(int code, String message, List<Product> productList) {
        super(code, message);
        this.productList = productList;
    }
}

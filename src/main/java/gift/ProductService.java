package gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static gift.Message.*;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getOneProduct(Long productId) {
        return productRepository.selectOneProduct(productId);
    }

    public List<Product> getProduct() {
        return productRepository.selectAllProducts();
    }

    public String addNewProduct(Product newProduct) {
        productRepository.insertProduct(newProduct);
        return ADD_SUCCESS_MSG;
    }

    public String updateProductInfo(Long productId, Product product) {

        Product productToUpdate = productRepository.selectOneProduct(productId);

        if (product.getName() != null) {
            productToUpdate.setName(product.getName());
        }
        if (product.getPrice() > 0) {
            productToUpdate.setPrice(product.getPrice());
        }
        if (product.getImageUrl() != null) {
            productToUpdate.setImageUrl(product.getImageUrl());
        }
        productRepository.updateProduct(productToUpdate);
        return UPDATE_SUCCESS_MSG;
    }

    public String deleteTheProduct(Long productId) {
        productRepository.deleteProduct(productId);
        return DELETE_SUCCESS_MSG;
    }

}

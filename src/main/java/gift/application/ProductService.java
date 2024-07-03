package gift.application;

import gift.domain.Product;
import gift.infra.ProductRepository;
import gift.presentation.WishListManageController.CreateProductRequestDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void addProduct(CreateProductRequestDTO createProductRequestDTO) {
        //TODO: Validation
        Product product = new Product(createProductRequestDTO.name(), createProductRequestDTO.price(),
            createProductRequestDTO.imageUrl());
        productRepository.addProduct(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }

    public void updateProduct(Long id, String name, Double price, String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        productRepository.updateProduct(id, product);
    }

    public Product getProductByName(Long id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getProduct() {
        return productRepository.getProducts();
    }

}

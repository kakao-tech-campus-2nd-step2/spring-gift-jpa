package gift.service;

import gift.entity.Product;

import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    public void updateProduct(Product product, Long id) {
        Product update = productRepository.findById(id).get();
        update.setName(product.getName());
        update.setPrice(product.getPrice());
        update.setImageUrl(product.getImageUrl());

        productRepository.save(update);


    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}

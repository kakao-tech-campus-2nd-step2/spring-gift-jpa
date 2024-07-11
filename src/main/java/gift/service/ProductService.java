package gift.service;

import gift.exception.ResourceNotFoundException;
import gift.model.Product;
import gift.model.ProductDTO;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, ProductDTO product) {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        foundProduct.setName(product.getName());
        foundProduct.setPrice(product.getPrice());
        foundProduct.setImageurl(product.getImageurl());
        return productRepository.save(foundProduct);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}

package gift.service;
import gift.domain.Product;
import gift.domain.ProductDTO;
import gift.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        Product product = new Product(1, "test", "imgURL");
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        try {
            return productRepository.findById(id);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    public Product addProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.getPrice(), productDTO.getName(), productDTO.getImgURL());
        try {
            return productRepository.save(product);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Product updateProduct(int id, ProductDTO productDTO) {
        Product product = new Product(id, productDTO.getPrice(), productDTO.getName(), productDTO.getImgURL());
        try {
            return productRepository.save(product);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No product found with id " + id);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void deleteProduct(int id) {
        try {
            productRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new NoSuchElementException();
        }
    }
}

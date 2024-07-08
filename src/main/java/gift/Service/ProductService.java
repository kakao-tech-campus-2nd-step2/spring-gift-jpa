package gift.Service;

import gift.Model.Product;
import gift.Repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id);
    }

    public void addProduct(Product product){
        productRepository.add(product);
    }

    public void updateProduct(Product product){
        productRepository.update(product);
    }

    public void deleteProduct(Long id){
        productRepository.delete(id);
    }

}

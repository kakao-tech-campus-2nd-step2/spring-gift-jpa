package gift.service;

import gift.exception.InvalidProductException;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(long id) {
        return productRepository.findById(id)
        		.orElseThrow(() -> new InvalidProductException("Product not found with id: " + id));
    }

    public Product createProduct(Product product, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
        }
        return productRepository.save(product);
    }

    public void updateProduct(long id, Product updatedProduct, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
    	}
    	if(!updatedProduct.getId().equals(id)) {
    		throw new InvalidProductException("Product Id mismatch.");
    	}
    	if(!productRepository.existsById(id)) {
    		throw new InvalidProductException("Product not found with id: " + id);
    	}
    	productRepository.save(updatedProduct);
    }

    public void deleteProduct(long id) {
        if(!productRepository.existsById(id)) {
        	throw new InvalidProductException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}

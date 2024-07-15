package gift.service;

import gift.exception.InvalidProductException;
import gift.exception.InvalidUserException;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProduct(long id) {
        return findProductById(id);
    }

    public Product createProduct(Product product, BindingResult bindingResult) {
    	validateBindingResult(bindingResult);
        return productRepository.save(product);
    }

    public void updateProduct(long id, Product updatedProduct, BindingResult bindingResult) {
    	validateBindingResult(bindingResult);
    	validProductId(id, updatedProduct);
    	validateProductId(id);
    	productRepository.save(updatedProduct);
    }

    public void deleteProduct(long id) {
    	validateProductId(id);
        productRepository.deleteById(id);
    }
    
    private void validateBindingResult(BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		String errorMessage = bindingResult
					.getFieldError()
					.getDefaultMessage();
			throw new InvalidUserException(errorMessage, HttpStatus.BAD_REQUEST);
    	}
    }
    
    private void validProductId(long id, Product updatedProduct) {
    	if(!updatedProduct.getId().equals(id)) {
    		throw new InvalidProductException("Product Id mismatch.");
    	}
    }
    
    private void validateProductId(long id) {
    	if(!productRepository.existsById(id)) {
    		throw new InvalidProductException("Product not found");
    	}
    }
    
    public Product findProductById(long id) {
	    return productRepository.findById(id)
	    		.orElseThrow(() -> new InvalidProductException("Product not found"));
    }
}
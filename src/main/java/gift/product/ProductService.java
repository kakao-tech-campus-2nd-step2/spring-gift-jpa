package gift.product;

import gift.Exception.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public HttpStatus createProduct(@Valid Product newProduct) throws IllegalArgumentException{
        productRepository.save(newProduct);
        return HttpStatus.CREATED;
    }

    public HttpStatus updateProduct(Product changeProduct) {
        productRepository.save(changeProduct);

        return HttpStatus.OK;
    }

    public HttpStatus deleteProduct(Long id) {
        productRepository.deleteById(id);

        return HttpStatus.OK;
    }
}
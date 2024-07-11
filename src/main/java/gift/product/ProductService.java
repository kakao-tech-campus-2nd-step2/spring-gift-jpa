package gift.product;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


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
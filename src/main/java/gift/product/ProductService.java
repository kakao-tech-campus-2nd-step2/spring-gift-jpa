package gift.product;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public HttpStatus createProduct(@Valid Product newProduct) throws IllegalArgumentException{
        productRepository.save(newProduct);
        return HttpStatus.CREATED;
    }

    public HttpStatus updateProduct(Product changeProduct) throws NotFoundException {
        Product product = productRepository.findById(changeProduct.getId()).orElseThrow(
            NotFoundException::new);
        product.update(changeProduct.getName(),changeProduct.getPrice(),changeProduct.getImageUrl());
        productRepository.save(product);
      
        return HttpStatus.OK;
    }

    public HttpStatus deleteProduct(Long id) {
        productRepository.deleteById(id);

        return HttpStatus.OK;
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }
}
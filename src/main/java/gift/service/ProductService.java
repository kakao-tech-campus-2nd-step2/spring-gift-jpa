package gift.service;

import gift.exception.NotFoundProductException;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getPagedAllProducts(Pageable pageable) {
        return productRepository.findPageBy(pageable);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(NotFoundProductException::new);
    }

    @Transactional
    public void addProduct(String name, Integer price, String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        productRepository.save(product);
    }

    @Transactional
    public void editProduct(Long id, String name, Integer price, String imageUrl) {
        productRepository.findById(id)
            .ifPresentOrElse(p -> p.updateProduct(name, price, imageUrl),
                () -> {
                    throw new NotFoundProductException();
                }
            );
    }

    @Transactional
    public void removeProduct(Long id) {
        productRepository.findById(id)
            .ifPresentOrElse(productRepository::delete
                , () -> {
                    throw new NotFoundProductException();
                }
            );
    }
}

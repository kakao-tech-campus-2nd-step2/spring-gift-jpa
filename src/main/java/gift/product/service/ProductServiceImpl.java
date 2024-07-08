package gift.product.service;

import gift.core.domain.product.Product;
import gift.core.domain.product.ProductRepository;
import gift.core.domain.product.ProductService;
import gift.core.domain.product.exception.ProductAlreadyExistsException;
import gift.core.domain.product.exception.ProductNotFoundException;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product get(Long id) {
        if (!productRepository.exists(id)) {
            throw new ProductNotFoundException();
        }
        return productRepository.get(id);
    }

    @Override
    public boolean exists(Long id) {
        return productRepository.exists(id);
    }

    @Override
    public void createProduct(@Nonnull Product product) {
        if (productRepository.exists(product.id())) {
            throw new ProductAlreadyExistsException();
        }
        productRepository.save(product);
    }

    @Override
    public void updateProduct(@Nonnull Product product) {
        if (!productRepository.exists(product.id())) {
            throw new ProductNotFoundException();
        }
        productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        if (!productRepository.exists(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.remove(id);
    }
}

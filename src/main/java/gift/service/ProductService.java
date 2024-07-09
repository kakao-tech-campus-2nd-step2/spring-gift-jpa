package gift.service;

import gift.entity.Product;

import gift.exception.DataNotFoundException;
import gift.exception.DuplicateUserEmailException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new DataNotFoundException("존재하지 않는 Product: Product를 찾을 수 없습니다.");
        }
        return product.get();
    }


    public void updateProduct(Product product, Long id) {
        Optional<Product> updateProduct = productRepository.findById(id);

        if (updateProduct.isEmpty()) {
            throw new DataNotFoundException("존재하지 않는 Product: Product를 Update할 수 없습니다.");
        }

        Product update = updateProduct.get();
        update.setName(product.getName());
        update.setPrice(product.getPrice());
        update.setImageUrl(product.getImageUrl());

        productRepository.save(update);


    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}

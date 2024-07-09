package gift.product.service;

import gift.product.dto.ProductDto;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductAll() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return getValidatedProduct(id);
    }

    public Product insertProduct(ProductDto productDto) {
        Product product = new Product(productDto.name(), productDto.price(), productDto.imageUrl());
        product = productRepository.save(product);

        return product;
    }

    public Product updateProduct(Long id, ProductDto productDTO) {
        getValidatedProduct(id);

        Product product = new Product(id, productDTO.name(), productDTO.price(),
            productDTO.imageUrl());
        productRepository.update(product);
        return product;
    }

    public void deleteProduct(Long id) {
        getValidatedProduct(id);
        productRepository.delete(id);
    }

    private Product getValidatedProduct(Long id) {
        try {
            return productRepository.findById(id);
        } catch (Exception e) {
            throw new NoSuchElementException("해당 ID의 상품이 존재하지 않습니다.");
        }
    }
}

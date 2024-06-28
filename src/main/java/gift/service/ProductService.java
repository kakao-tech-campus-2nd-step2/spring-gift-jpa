package gift.service;

import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.dto.UpdateProductDto;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    //private final Map<Long, Product> products = new HashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    public Product createProduct(CreateProductDto productDto) {
        Product product = new Product();

        if (productDto.getName() == null || productDto.getDescription() == null || productDto.getPrice() == null || productDto.getImageUrl() == null) {
            throw new IllegalArgumentException("상품의 이름, 가격, 설명을 모두 입력해야합니다.");
        }
        product.setId(id.getAndIncrement());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());

        productRepository.save(product);

        return product;

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long productId) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }
        return product;
    }

    public Product updateProduct(Long productId, UpdateProductDto productDto) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());

        productRepository.update(product);
        return product;
    }

    public void deleteProduct(Long productId) {
        Product product = products.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        products.remove(productId);
    }
}

package gift.service;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));
        return products.map(ProductResponseDTO::new);
    }

    public Optional<ProductResponseDTO> getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(ProductResponseDTO::new);
    }

    public Product findProductEntityById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품 정보를 찾을 수 없습니다."));
    }

    public Page<ProductResponseDTO> createProduct(ProductRequestDTO productRequest, int page, int size) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        productRepository.save(product);
        return getAllProducts(page, size);
    }

    public Optional<ProductResponseDTO> updateProduct(Long id, ProductRequestDTO productRequest) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            existingProduct.setName(productRequest.getName());
            existingProduct.setImageUrl(productRequest.getImageUrl());
            existingProduct.setPrice(productRequest.getPrice());
            productRepository.save(existingProduct);
            return Optional.of(new ProductResponseDTO(existingProduct));
        }
        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

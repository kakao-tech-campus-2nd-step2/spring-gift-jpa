package gift.service;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        for (Product product : products) {
            productResponseDTOs.add(new ProductResponseDTO(product));
        }
        return productResponseDTOs;
    }

    public Optional<ProductResponseDTO> getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(ProductResponseDTO::new);
    }

    public List<ProductResponseDTO> createProduct(ProductRequestDTO productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        return getAllProducts();
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

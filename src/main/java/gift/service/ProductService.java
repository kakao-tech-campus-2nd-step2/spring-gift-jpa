package gift.service;

import gift.domain.Product;
import gift.dto.requestDTO.ProductRequestDTO;
import gift.dto.responseDTO.ProductListResponseDTO;
import gift.dto.responseDTO.ProductResponseDTO;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductListResponseDTO getAllProducts(){
        List<ProductResponseDTO> productResponseDTOList = productRepository.selectAllProduct()
            .stream()
            .map(ProductResponseDTO::of)
            .toList();

        return new ProductListResponseDTO(productResponseDTOList);
    }

    public ProductResponseDTO getOneProduct(Long productId){
        Product product = productRepository.selectProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        return ProductResponseDTO.of(product);
    }

    public Long addProduct(ProductRequestDTO productRequestDTO){
        Product product = new Product(productRequestDTO.name(),
            productRequestDTO.price(), productRequestDTO.imageUrl());
        return productRepository.insertProduct(product);
    }

    public Long updateProduct(Long productId, ProductRequestDTO productRequestDTO){
        Product product = productRepository.selectProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));

        product.update(productRequestDTO.name(), productRequestDTO.price(),
            productRequestDTO.imageUrl());
        productRepository.updateProduct(product);
        return product.getId();
    }

    public Long deleteProduct(Long productId){
        Product product = productRepository.selectProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        productRepository.deleteProduct(productId);
        return product.getId();
    }
}

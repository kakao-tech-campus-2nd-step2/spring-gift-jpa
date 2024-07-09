package gift.service;

import gift.domain.Product;
import gift.dto.requestDTO.ProductRequestDTO;
import gift.dto.responseDTO.ProductListResponseDTO;
import gift.dto.responseDTO.ProductResponseDTO;
import gift.repository.JpaProductRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class ProductService {
    private final JpaProductRepository jpaproductRepository;

    public ProductService(JpaProductRepository jpaproductRepository) {
        this.jpaproductRepository = jpaproductRepository;
    }

    public ProductListResponseDTO getAllProducts(){
        List<ProductResponseDTO> productResponseDTOList = jpaproductRepository.findAll()
            .stream()
            .map(ProductResponseDTO::of)
            .toList();

        return new ProductListResponseDTO(productResponseDTOList);
    }

    public ProductResponseDTO getOneProduct(Long productId){
        Product product = jpaproductRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        return ProductResponseDTO.of(product);
    }

    public Long addProduct(ProductRequestDTO productRequestDTO){
        Product product = new Product(productRequestDTO.name(),
            productRequestDTO.price(), productRequestDTO.imageUrl());
        return jpaproductRepository.save(product).getId();
    }

    public Long updateProduct(Long productId, ProductRequestDTO productRequestDTO){
        Product product = jpaproductRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));

        product.update(productRequestDTO.name(), productRequestDTO.price(),
            productRequestDTO.imageUrl());
        return product.getId();
    }

    public Long deleteProduct(Long productId){
        Product product = jpaproductRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        jpaproductRepository.delete(product);
        return product.getId();
    }
}

package gift.service;

import gift.domain.Product;
import gift.dto.requestDTO.ProductRequestDTO;
import gift.dto.responseDTO.ProductListResponseDTO;
import gift.dto.responseDTO.ProductResponseDTO;
import gift.repository.JpaProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductService {
    private final JpaProductRepository jpaProductRepository;

    public ProductService(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Transactional(readOnly = true)
    public ProductListResponseDTO getAllProducts() {
        List<ProductResponseDTO> productResponseDTOList = jpaProductRepository.findAll()
            .stream()
            .map(ProductResponseDTO::of)
            .toList();

        return new ProductListResponseDTO(productResponseDTOList);
    }

    @Transactional(readOnly = true)
    public ProductListResponseDTO getAllProducts(int page, int size, String criteria) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(criteria));
        List<ProductResponseDTO> productResponseDTOList = jpaProductRepository.findAll(pageable)
            .stream()
            .map(ProductResponseDTO::of)
            .toList();

        return new ProductListResponseDTO(productResponseDTOList);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getOneProduct(Long productId) {
        Product product = jpaProductRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        return ProductResponseDTO.of(product);
    }

    public Long addProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product(productRequestDTO.name(),
            productRequestDTO.price(), productRequestDTO.imageUrl());
        return jpaProductRepository.save(product).getId();
    }

    public Long updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = jpaProductRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));

        product.update(productRequestDTO.name(), productRequestDTO.price(),
            productRequestDTO.imageUrl());
        return product.getId();
    }

    public Long deleteProduct(Long productId) {
        Product product = jpaProductRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        jpaProductRepository.delete(product);
        return product.getId();
    }
}

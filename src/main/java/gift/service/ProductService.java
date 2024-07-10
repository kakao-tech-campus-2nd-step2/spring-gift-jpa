package gift.service;

import gift.DTO.ProductDTO;
import gift.model.product.ProductEntity;
import gift.model.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductDTO toProductDTO(ProductEntity productEntity) {
        return new ProductDTO(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getPrice(),
            productEntity.getImageUrl()
        );
    }

    private ProductEntity toProductEntity(ProductDTO productDTO) {
        var productEntity = new ProductEntity();
        productEntity.setId(productDTO.id());
        productEntity.setName(productDTO.name());
        productEntity.setPrice(productDTO.price());
        productEntity.setImageUrl(productDTO.imageUrl());
        return productEntity;
    }

    /**
     * 새 상품을 생성하고 맵에 저장함
     *
     * @param productDTO 저장할 상품 객체
     */
    public ProductDTO createProduct(ProductDTO productDTO) {
        var productEntity = toProductEntity(productDTO);
        productRepository.save(productEntity);
        return toProductDTO(productEntity);
    }

    /**
     * 주어진 ID에 해당하는 상품을 반환함
     *
     * @param id 조회할 상품의 ID
     */
    public ProductDTO getProduct(Long id) {
        productNotFoundDetector(id);
        var productEntity = productRepository.findById(id).get();
        return toProductDTO(productEntity);
    }

    /**
     * 모든 상품을 반환함
     */
    public List<ProductDTO> getAllProducts() {
        var productEntities = productRepository.findAll();
        return productEntities.stream().map(this::toProductDTO).toList();
    }

    /**
     * 주어진 ID에 해당하는 상품을 삭제함
     *
     * @param id 삭제할 상품의 ID
     */
    public void deleteProduct(Long id) {
        productNotFoundDetector(id);
        productRepository.deleteById(id);
    }

    /**
     * 주어진 상품을 갱신함
     *
     * @param productDTO 갱신할 상품 객체
     */
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        productNotFoundDetector(id);
        var productEntity = toProductEntity(productDTO);
        productEntity.setId(id);
        productRepository.save(productEntity);
        return toProductDTO(productEntity);
    }

    /**
     * 주어진 ID에 해당하는 상품이 존재하는지 확인함
     *
     * @param id 확인할 상품의 ID
     * @return 상품이 존재하면 true, 그렇지 않으면 false
     */
    public boolean exists(Long id) {
        return productRepository.existsById(id);
    }

    /**
     * 상품이 존재하지 않을 때 예외를 발생시킴
     *
     * @param id 확인할 상품의 ID
     */
    public void productNotFoundDetector(Long id) {
        if (!exists(id)) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }
    }

}

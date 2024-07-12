package gift.service;

import gift.DTO.ProductDTO;
import gift.aspect.CheckProductExists;
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
    @CheckProductExists
    public ProductDTO getProduct(Long id) {
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
    @CheckProductExists
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * 주어진 상품을 갱신함
     *
     * @param productDTO 갱신할 상품 객체
     */
    @CheckProductExists
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        var productEntity = toProductEntity(productDTO);
        productEntity.setId(id);
        productRepository.save(productEntity);
        return toProductDTO(productEntity);
    }

}

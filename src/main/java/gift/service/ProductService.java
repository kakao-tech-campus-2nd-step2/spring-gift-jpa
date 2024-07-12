package gift.service;

import gift.domain.Product;
import gift.entity.ProductEntity;
import gift.error.AlreadyExistsException;
import gift.error.NotFoundException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //전체 상품 조회 기능
    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable) {
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        return productEntities.map(this::entityToDto);
    }

    //단일 상품 조회 기능
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return entityToDto(productEntity);

    }

    //이름을 통한 상품 검색 기능
    @Transactional(readOnly = true)
    public List<Product> searchProduct(String name) {
        List<ProductEntity> productEntities = productRepository.findByNameContaining(name);
        return productEntities.stream()
            .map(this::entityToDto)
            .collect(Collectors.toList());
    }

    //상품 추가 기능
    @Transactional
    public void addProduct(Product product) {
        checkAlreadyExists(product);
        productRepository.save(dtoToEntity(product));
    }

    //상품 수정 기능
    @Transactional
    public void updateProduct(Long id, Product product) {
        ProductEntity existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        checkAlreadyExists(product);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImageUrl(product.getImageUrl());
        productRepository.save(existingProduct);
    }

    //상품 삭제 기능
    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(existingProduct);
    }

    private void checkAlreadyExists(Product product) {
        boolean exists = productRepository.findAll().stream()
            .anyMatch(p -> p.getName().equals(product.getName()) &&
                p.getPrice().equals(product.getPrice()) &&
                p.getImageUrl().equals(product.getImageUrl()));
        if (exists) {
            throw new AlreadyExistsException("해당 상품이 이미 존재 합니다!");
        }
    }

    private Product entityToDto(ProductEntity productEntity) {
        return new Product(productEntity.getName(), productEntity.getPrice(),
            productEntity.getImageUrl());
    }

    private ProductEntity dtoToEntity(Product dto) {
        return new ProductEntity(dto.getName(), dto.getPrice(), dto.getImageUrl());
    }

}

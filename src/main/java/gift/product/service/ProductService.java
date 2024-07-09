package gift.product.service;

import gift.product.domain.ProductEntity;
import gift.product.error.AlreadyExistsException;
import gift.product.domain.Product;
import gift.product.error.NotFoundException;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //전체 상품 조회 기능
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities =  productRepository.findAll();
        return productEntities.stream()
            .map(this::entityToDto)
            .collect(Collectors.toList());
    }

    //단일 상품 조회 기능
    public Product getProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return entityToDto(productEntity);

    }

    public List<Product> searchProduct(String name) {
        List<ProductEntity> productEntities = productRepository.findByNameContaining(name);
        return productEntities.stream()
            .map(this::entityToDto)
            .collect(Collectors.toList());
    }

    //상품 추가 기능
    public void addProduct(Product product) {
        checkAlreadyExists(product);
        productRepository.save(dtoToEntity(product));
    }

    //상품 수정 기능
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
    public void deleteProduct(Long id) {
        ProductEntity existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(existingProduct);
    }

    private void checkAlreadyExists(Product product) {
        List<ProductEntity> products = productRepository.findAll();
        for (ProductEntity p : products) {
            if (p.getName().equals(product.getName()) &&
                p.getPrice().equals(product.getPrice()) &&
                p.getImageUrl().equals(product.getImageUrl())) {
                throw new AlreadyExistsException("해당 상품이 이미 존재 합니다!");
            }
        }
    }

    private Product entityToDto(ProductEntity productEntity) {
        return new Product(productEntity.getName(), productEntity.getPrice(), productEntity.getImageUrl());
    }

    private ProductEntity dtoToEntity(Product dto) {
        return new ProductEntity(dto.getName(), dto.getPrice(), dto.getImageUrl());
    }

}

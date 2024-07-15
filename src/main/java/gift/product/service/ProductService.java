package gift.product.service;
import gift.product.dto.ProductDto;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Page<ProductDto> findAll(Pageable pageable) {
    try {
      return productRepository.findAll(pageable).map(Product::toDto);
    } catch (Exception e) {
      throw new RuntimeException("모든 상품을 조회하는 중에 오류가 발생했습니다.", e);
    }
  }

  public Optional<ProductDto> getProductById(long id) {
    try {
      return productRepository.findById(id).map(Product::toDto);
    } catch (Exception e) {
      throw new RuntimeException("ID가 " + id + "인 상품을 조회하는 중에 오류가 발생했습니다.", e);
    }
  }

  @Transactional
  public ProductDto addProduct(ProductDto productDto) {
    try {
      Product product = Product.fromDto(productDto);
      validateProduct(product);
      Product savedProduct = productRepository.save(product);
      return savedProduct.toDto();
    } catch (Exception e) {
      throw new RuntimeException("상품을 추가하는 중에 오류가 발생했습니다.", e);
    }
  }

  @Transactional
  public ProductDto updateProduct(long id, ProductDto productDto) {
    try {
      Product existingProduct = productRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("ID가 " + id + "인 상품이 존재하지 않습니다."));
      Product updatedProduct = Product.fromDto(productDto);
      updatedProduct.setId(existingProduct.getId());
      validateProduct(updatedProduct);
      Product savedProduct = productRepository.save(updatedProduct);
      return savedProduct.toDto();
    } catch (Exception e) {
      throw new RuntimeException("상품을 업데이트하는 중에 오류가 발생했습니다.", e);
    }
  }

  public void deleteProduct(long id) {
    try {
      productRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("ID가 " + id + "인 상품을 삭제하는 중에 오류가 발생했습니다.", e);
    }
  }

  private void validateProduct(Product product) {
    if (product.getName() == null || product.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("상품 이름은 비어 있을 수 없습니다.");
    }
    if (product.getPrice() <= 0) {
      throw new IllegalArgumentException("상품 가격은 0보다 커야 합니다.");
    }
    if (product.getImageUrl() == null || product.getImageUrl().trim().isEmpty()) {
      throw new IllegalArgumentException("상품 이미지 URL은 비어 있을 수 없습니다.");
    }
  }


  private Product convertToEntity(ProductDto productDto) {
    Product product = new Product();
    product.setId(productDto.getId());
    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    product.setImageUrl(productDto.getImageUrl());
    return product;
  }

  private ProductDto convertToDto(Product product) {
    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getImageUrl()
    );
  }
}

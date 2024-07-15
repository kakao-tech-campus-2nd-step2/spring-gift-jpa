package gift.service;

import gift.exception.KakaoValidationException;
import gift.exception.StringValidationException;
import gift.model.Product;
import gift.model.ProductDto;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Page<ProductDto> findAll(Pageable pageable) {
    return productRepository.findAll(pageable).map(this::convertToDto);
  }

  public Optional<ProductDto> findById(Long id) {
    return productRepository.findById(id).map(this::convertToDto);
  }

  public ProductDto save(ProductDto productDto) {
    validate(productDto);
    Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
    Product savedProduct = productRepository.save(product);
    return convertToDto(savedProduct);
  }

  public boolean updateProduct(Long id, ProductDto productDetails) {
    validate(productDetails);
    return productRepository.findById(id).map(product -> {
      product.update(productDetails.getName(), productDetails.getPrice(), productDetails.getImageUrl());
      productRepository.save(product);
      return true;
    }).orElse(false);
  }

  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }

  private ProductDto convertToDto(Product product) {
    return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
  }
  public Product findProductById(Long productId) {
    return productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
  }

  private void validate(ProductDto productDto) {
    String name = productDto.getName();
    if (name.contains("카카오")) {
      throw new KakaoValidationException("상품 이름에 '카카오'를 포함하려면 담당 MD와 협의가 필요합니다.");
    }
    if (!name.matches("^[\\p{L}\\p{N}\\s\\(\\)\\[\\]\\+\\-\\&\\/]*$")) {
      throw new StringValidationException("허용되지 않은 특수기호는 사용할 수 없습니다. 허용된 특수기호:( ), [ ], +, -, &, /, _");
    }
  }
}

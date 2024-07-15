package gift.service;
import gift.entity.Product;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> findAll() {
    try {
      return productRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("모든 상품을 조회하는 중에 오류가 발생했습니다.", e);
    }
  }

  public Optional<Product> getProductById(long id) {
    try {
      return productRepository.findById(id);
    } catch (Exception e) {
      throw new RuntimeException("ID가 " + id + "인 상품을 조회하는 중에 오류가 발생했습니다.", e);
    }
  }

  public Product addProduct(Product product) {
    try {
      validateProduct(product);
      return productRepository.save(product);
    } catch (Exception e) {
      throw new RuntimeException("상품을 추가하는 중에 오류가 발생했습니다.", e);
    }
  }

  public Product updateProduct(Product product) {
    try {
      validateProduct(product);
      return productRepository.save(product);
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
}


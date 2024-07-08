package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public Optional<Product> findById(Long id) {
    return productRepository.findById(id);
  }

  public Product save(Product product) {
    productRepository.save(product);
    return product;
  }

  public boolean updateProduct(Long id, Product product) {
    if (productRepository.findById(id).isPresent()) {
      product.setId(id);
      productRepository.update(product);
      return true;
    }
    return false;
  }

  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }
}
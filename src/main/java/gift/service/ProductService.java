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
    return productRepository.save(product);
  }

  public boolean updateProduct(Long id, Product productDetails) {
    return productRepository.findById(id).map(product -> {
      product.setName(productDetails.getName());
      product.setPrice(productDetails.getPrice());
      product.setImageUrl(productDetails.getImageUrl());
      productRepository.save(product);
      return true;
    }).orElse(false);
  }

  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }
}
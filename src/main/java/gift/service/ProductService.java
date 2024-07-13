package gift.service;

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
    Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
    Product savedProduct = productRepository.save(product);
    return convertToDto(savedProduct);
  }

  public boolean updateProduct(Long id, ProductDto productDetails) {
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
}

package gift.Service;

import gift.ConverterToDto;
import gift.DTO.Product;
import gift.DTO.ProductDto;
import gift.Repository.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Page<ProductDto> getAllProducts(Pageable pageable) {
    Page<Product> products = productRepository.findAll(pageable);
    Page<ProductDto> productDtos = products.map(ConverterToDto::convertToProductDto);

    return productDtos;
  }

  public ProductDto getProductById(Long id) {
    Product product = (productRepository.findById(id)
      .orElseThrow(() -> new EmptyResultDataAccessException("해당 상품이 없습니다.", 1)));

    return ConverterToDto.convertToProductDto(product);
  }

  public ProductDto addProduct(ProductDto productDto) {
    Product product = new Product(productDto.getId(), productDto.getName(),
      productDto.getPrice(), productDto.getImageUrl());
    productRepository.save(product);

    return productDto;
  }

  public ProductDto updateProduct(Long id, ProductDto updatedProductDto) {
    Product existingProduct = productRepository.findById(id)
      .orElseThrow(() -> new EmptyResultDataAccessException("해당 상품이 없습니다.", 1));
    Product newProduct = new Product(id,
      updatedProductDto.getName(), updatedProductDto.getPrice(),
      updatedProductDto.getImageUrl());
    productRepository.deleteById(id);
    productRepository.save(newProduct);

    return ConverterToDto.convertToProductDto(newProduct);
  }

  public ProductDto deleteProduct(@PathVariable Long id) {
    Product existingProduct = productRepository.findById(id)
      .orElseThrow(() -> new EmptyResultDataAccessException("해당 데이터가 없습니다", 1));
    productRepository.deleteById(id);

    return ConverterToDto.convertToProductDto(existingProduct);
  }

}

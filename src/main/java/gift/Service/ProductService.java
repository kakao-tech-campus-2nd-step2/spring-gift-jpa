package gift.Service;

import gift.DTO.ProductDto;
import gift.DTO.Product;
import gift.ConverterToDto;
import gift.Repository.ProductRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }


  public List<ProductDto> getAllProducts() {
    List<Product> product = productRepository.findAll();
    List<ProductDto> productDtos = product.stream()
      .map(ConverterToDto::convertToProductDto)
      .collect(Collectors.toList());
    return productDtos;
  }

  public ProductDto getProductById(Long id) {
    Optional<Product> productOptional = productRepository.findById(id);

    //Optional에 ProductConverter::convertToDto를 직접 이용 못하므로, map 이용하여 entity 뽑아낸 후에 적용
    return productOptional.map(ConverterToDto::convertToProductDto).get();
  }

  public ProductDto addProduct(@Valid ProductDto productDto) {
    Product product = new Product(productDto.getId(), productDto.getName(),
      productDto.getPrice(), productDto.getImageUrl());
    productRepository.save(product);
    return productDto;
  }
  public ProductDto updateProduct(Long id, @Valid ProductDto updatedProductDto) {
    Optional<Product> existingProductOptional = productRepository.findById(id);
    Product newProduct = new Product(id,
      updatedProductDto.getName(), updatedProductDto.getPrice(),
      updatedProductDto.getImageUrl());
    productRepository.deleteById(id);
    productRepository.save(newProduct);
    return existingProductOptional.map(ConverterToDto::convertToProductDto).get();
  }

  public ProductDto deleteProduct(@PathVariable Long id) {
    Optional<Product> existingProductOptional = Optional.ofNullable(productRepository.findById(id)
      .orElseThrow(() -> new EmptyResultDataAccessException("해당 데이터가 없습니다", 1)));
    productRepository.deleteById(id);

    return existingProductOptional.map(ConverterToDto::convertToProductDto).get();
  }

}

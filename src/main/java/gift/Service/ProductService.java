package gift.Service;

import gift.DTO.ProductDto;
import gift.DTO.Product;
import gift.ConverterToDto;
import gift.Repository.ProductDao;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProductService {

  private final ProductDao productDao;

  public ProductService(ProductDao productDao) {
    this.productDao = productDao;
  }


  public List<ProductDto> getAllProducts() {
    List<Product> productEntities = productDao.findAll();
    List<ProductDto> productDtos = productEntities.stream()
      .map(ConverterToDto::convertToProductDto)
      .collect(Collectors.toList());
    return productDtos;
  }

  public Optional<ProductDto> getProductById(Long id) {
    Optional<Product> productEntityOptional = productDao.findById(id);

    //Optional에 ProductConverter::convertToDto를 직접 이용 못하므로, map 이용하여 entity 뽑아낸 후에 적용
    return productEntityOptional.map(ConverterToDto::convertToProductDto);
  }

  public ProductDto addProduct(@Valid ProductDto productDto) {
    Product product = new Product(productDto.getId(), productDto.getName(),
      productDto.getPrice(), productDto.getImageUrl());
    productDao.save(product);
    return productDto;
  }
  public Optional<ProductDto> updateProduct(Long id, @Valid ProductDto updatedProductDto) {
    Optional<Product> existingProductEntityOptional = productDao.findById(id);
    Product newProduct = new Product(id,
      updatedProductDto.getName(), updatedProductDto.getPrice(),
      updatedProductDto.getImageUrl());
    productDao.deleteById(id);
    productDao.save(newProduct);
    return existingProductEntityOptional.map(ConverterToDto::convertToProductDto);
  }

  public Optional<ProductDto> deleteProduct(@PathVariable Long id) {
    Optional<Product> existingProductEntityOptional = productDao.findById(id);
    if (existingProductEntityOptional == null) {
      throw new EmptyResultDataAccessException("해당 데이터가 없습니다", 1);
    }
    productDao.deleteById(id);

    return existingProductEntityOptional.map(ConverterToDto::convertToProductDto);
  }

}

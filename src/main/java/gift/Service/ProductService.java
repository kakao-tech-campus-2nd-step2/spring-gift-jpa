package gift.Service;

import gift.DTO.ProductDto;
import gift.Repository.ProductDao;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
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
    List<ProductDto> ProductDtos = productDao.findAll();
    return ProductDtos;
  }

  public Optional<ProductDto> getProductById(Long id) {
    return productDao.findById(id);
  }

  public ProductDto addProduct(@Valid ProductDto productDTO) {
    productDao.save(productDTO);
    return productDTO;
  }

  public Optional<ProductDto> updateProduct(Long id, @Valid ProductDto updatedProductDto) {
    Optional<ProductDto> existingProductDto = productDao.findById(id);
    ProductDto newProduct = new ProductDto(id,
      updatedProductDto.getName(), updatedProductDto.getPrice(), updatedProductDto.getImageUrl());
    productDao.deleteById(id);
    productDao.save(newProduct);
    return Optional.of(newProduct);
  }

  public Optional<ProductDto> deleteProduct(@PathVariable Long id) {
    Optional<ProductDto> existingProductDto = productDao.findById(id);
    if (existingProductDto == null) {
      throw new EmptyResultDataAccessException("해당 데이터가 없습니다", 1);
    }
    productDao.deleteById(id);

    return existingProductDto;
  }

}

package gift.Service;

import gift.DTO.ProductEntity;
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

  public List<ProductEntity> getAllProducts() {
    List<ProductEntity> productEntities = productDao.findAll();
    return productEntities;
  }

  public Optional<ProductEntity> getProductById(Long id) {
    return productDao.findById(id);
  }

  public ProductEntity addProduct(@Valid ProductEntity productEntity) {
    productDao.save(productEntity);
    return productEntity;
  }

  public Optional<ProductEntity> updateProduct(Long id, @Valid ProductEntity updatedProductEntity) {
    Optional<ProductEntity> existingProductDto = productDao.findById(id);
    ProductEntity newProduct = new ProductEntity(id,
      updatedProductEntity.getName(), updatedProductEntity.getPrice(), updatedProductEntity.getImageUrl());
    productDao.deleteById(id);
    productDao.save(newProduct);
    return Optional.of(newProduct);
  }

  public Optional<ProductEntity> deleteProduct(@PathVariable Long id) {
    Optional<ProductEntity> existingProductDto = productDao.findById(id);
    if (existingProductDto == null) {
      throw new EmptyResultDataAccessException("해당 데이터가 없습니다", 1);
    }
    productDao.deleteById(id);

    return existingProductDto;
  }

}

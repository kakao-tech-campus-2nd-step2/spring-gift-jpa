package gift.Service;

import gift.DTO.productDto;
import gift.Repository.ProductDao;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProductService {
  private final ProductDao productDao;
  public ProductService(ProductDao productDao){
    this.productDao=productDao;
  }

  public List<productDto> getAllProducts() {
    List<productDto> productDtos = productDao.selectAllProducts();
    return productDtos;
  }

  public productDto getProductById(Long id) {
    return productDao.selectProduct(id);

  }

  public productDto addProduct(@Valid productDto productDTO){
    productDao.insertProduct(productDTO);
    return productDTO;
  }
  
  public productDto updateProduct(Long id, @Valid productDto updatedProductDto) {
    productDto existingProductDto = productDao.selectProduct(id);
    if (existingProductDto !=null){
      updatedProductDto.setId(id);
      productDao.updateProduct(updatedProductDto);
    }
    return existingProductDto;
  }

  public productDto deleteProduct(@PathVariable Long id) {
    productDto existingProductDto = productDao.selectProduct(id);
    if (existingProductDto != null) {
      productDao.deleteProduct(id);
    }
    return existingProductDto;
  }

}

package gift.Service;

import gift.DTO.ProductDTO;
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

  public List<ProductDTO> getAllProducts() {
    List<ProductDTO> productDTODtos = productDao.selectAllProducts();
    return productDTODtos;
  }

  public ProductDTO getProductById(Long id) {
    return productDao.selectProduct(id);

  }

  public ProductDTO addProduct(@Valid ProductDTO productDTO){
    productDao.insertProduct(productDTO);
    return productDTO;
  }
  
  public ProductDTO updateProduct(Long id, @Valid ProductDTO updatedProductDTO) {
    ProductDTO existingProductDTO = productDao.selectProduct(id);
    if (existingProductDTO !=null){
      updatedProductDTO.setId(id);
      productDao.updateProduct(updatedProductDTO);
    }
    return existingProductDTO;
  }

  public ProductDTO deleteProduct(@PathVariable Long id) {
    ProductDTO existingProductDTO = productDao.selectProduct(id);
    if (existingProductDTO != null) {
      productDao.deleteProduct(id);
    }
    return existingProductDTO;
  }

}

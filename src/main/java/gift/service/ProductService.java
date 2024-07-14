package gift.service;

import gift.dto.ProductDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    void saveProduct(ProductDTO productDTO);
    void updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    Page<ProductDTO> getProducts(Pageable pageable);
}



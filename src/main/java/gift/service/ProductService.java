package gift.service;

import gift.dto.ProductDTO;
import gift.DAO.ProductDAO;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDAO productDAO;

    @Autowired
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Collection<ProductDTO> getProducts() {
        return productDAO.getProducts();
    }

    public ProductDTO getProduct(Long id) {
        return productDAO.getProduct(id);
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        long id = productDAO.addProduct(productDTO);
        return productDAO.getProduct(id);
    }

    public ProductDTO updateProduct(long id, ProductDTO productDTO) {
        productDAO.getProduct(id);
        ProductDTO updatedProduct = new ProductDTO(id, productDTO.name(), productDTO.price(), productDTO.imageUrl());
        productDAO.updateProduct(updatedProduct);
        return updatedProduct;
    }

    public ProductDTO deleteProduct(long id) {
        ProductDTO deletedProduct = productDAO.getProduct(id);
        productDAO.deleteProduct(id);
        return deletedProduct;
    }
}

package gift.service;

import gift.dao.ProductDAO;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Product getProductById(Long id) {
        try {
            return productDAO.findById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException("해당 id를 가지고있는 Product 객체가 없습니다.");
        }
    }

    public void saveProduct(Product product) {
        if (product.getId() == null) {
            productDAO.save(product);
            return;
        }
        productDAO.update(product);
    }

    public void deleteProduct(Long id) {
        productDAO.deleteById(id);
    }
}

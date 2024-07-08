package gift.service;

import gift.dao.ProductDAO;
import gift.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return productDAO.findById(id);
    }

    public void addProduct(Product product) {
        productDAO.save(product);
    }

    public void updateProduct(Long id, Product product) {
        productDAO.update(id, product);
    }

    public void deleteProduct(Long id) {
        productDAO.delete(id);
    }
}

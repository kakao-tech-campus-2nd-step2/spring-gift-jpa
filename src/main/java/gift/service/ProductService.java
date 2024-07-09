package gift.service;
import gift.domain.Product;
import gift.domain.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
        productDAO.create();
        productDAO.insert(new Product(1, "test", 1, "test"));
    }

    public List<Product> getAllProducts() {
        return productDAO.selectAll();
    }

    public Product getProductById(long id) {
        try {
            return productDAO.select(id);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    public void addProduct(Product product) {
        try {
            productDAO.insert(product);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void updateProduct(long id, Product product) {
        try {
            productDAO.update(id, product);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No product found with id " + id);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void deleteProduct(long id) {
        try {
            productDAO.delete(id);
        }
        catch (Exception e) {
            throw new NoSuchElementException();
        }
    }
}

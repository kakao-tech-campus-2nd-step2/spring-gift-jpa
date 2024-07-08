package gift.product.service;

import gift.product.dao.AdminProductDao;
import gift.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdminProductService {

    private final AdminProductDao adminProductDao;

    @Autowired
    public AdminProductService(AdminProductDao adminProductDao) {
        this.adminProductDao = adminProductDao;
        adminProductDao.createProductTable();
    }

    public void registerProduct(Product product) {
        adminProductDao.registerProduct(product);
    }

    public void updateProduct(Product product) {
        adminProductDao.updateProduct(product);
    }

    public void deleteProduct(Long id) {
        adminProductDao.deleteProduct(id);
    }

    public Collection<Product> getAllProducts() {
        return adminProductDao.getAllProducts();
    }

    public Product getProductById(Long id) {
        return adminProductDao.getProductById(id);
    }

    public Collection<Product> searchProducts(String keyword) {
        return adminProductDao.searchProduct(keyword);
    }

    public boolean existsById(Long id) {
        return adminProductDao.existsById(id);
    }
}

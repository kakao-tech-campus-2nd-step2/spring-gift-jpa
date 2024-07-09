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
    }

    public void registerProduct(Product product) {
        adminProductDao.save(product);
    }

    public void updateProduct(Product product) {
        adminProductDao.save(product);
    }

    public void deleteProduct(Long id) {
        adminProductDao.deleteById(id);
    }

    public Collection<Product> getAllProducts() {
        return adminProductDao.findAll();
    }

    public Product getProductById(Long id) {
        return adminProductDao.findById(id).orElse(null);
    }

    public Collection<Product> searchProducts(String keyword) {
        return adminProductDao.findByName(keyword);
    }

    public boolean existsById(Long id) {
        return adminProductDao.existsById(id);
    }
}

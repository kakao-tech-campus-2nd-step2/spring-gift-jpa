package gift;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts(){
        return productDao.findAll();
    }

    public Product getProductById(long id) {
        return productDao.findById(id);
    }

    public Long addProduct(Product newProduct){
        return productDao.insertProduct(newProduct);
    }

    public Long updateProduct(long id, Product updatedProduct){
        return productDao.updateProduct(id, updatedProduct);
    }

    public Long deleteProduct(long id){
        return productDao.deleteById(id);
    }
}

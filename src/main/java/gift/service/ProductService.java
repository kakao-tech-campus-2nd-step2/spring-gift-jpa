package gift.service;


import gift.dto.ProductDto;
import gift.model.product.Product;
import gift.dao.ProductDao;
import gift.model.product.ProductName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public boolean addNewProduct(ProductDto productDto){
        Product product = new Product(productDto.id(),new ProductName(productDto.name()),productDto.price(),productDto.imageUrl(),productDto.amount());
        if (productDao.isProductExist(product.getId())) {
            return false;
        }
        productDao.insertProduct(product);
        return true;
    }

    public boolean updateProduct(Long id, ProductDto productDto) {
        Product product = new Product(productDto.id(),new ProductName(productDto.name()),productDto.price(),productDto.imageUrl(),productDto.amount());
        if (productDao.isProductExist(id)) {
            productDao.updateProduct(product);
            return true;
        }
        return false;
    }

    public boolean purchaseProduct(Long id, int amount) {
        Product product = productDao.selectProduct(id);
        if (product.isProductEnough(amount)) {
            productDao.purchaseProduct(id, amount);
            return true;
        }
        return false;
    }

    public Product selectProduct(Long id) {
        return productDao.selectProduct(id);
    }

    public List<Product> selectAllProducts(){
        return productDao.selectAllProducts();
    }

    public void DeleteProduct(Long id){
        productDao.deleteProduct(id);
    }
}

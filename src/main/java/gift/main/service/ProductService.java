package gift.main.service;

import gift.main.dto.ProductDto;
import gift.main.entity.Product;
import gift.main.repository.ProductDao;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;


import java.util.List;

@Service
@Validated
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getProducts() {
        List<Product> products= productDao.selectProductAll();
        return products;
    }

    public void addProduct(@Valid ProductDto validProduct) {
        productDao.insertProduct(validProduct);
    }

    public void deleteProduct(long id) {
        if (!productDao.existsProduct(id)) {
            throw new IllegalArgumentException("해당 id는 없습니다.");
        }
        productDao.deleteProduct(id);
    }

    public void updateProduct(long id,@Valid ProductDto validProduct) {
        if (!productDao.existsProduct(id)) {
            throw new IllegalArgumentException("해당 id는 없습니다.");
        }
        productDao.updateProduct(id, validProduct);
    }

    public Product getProduct(long id) {
        if (!productDao.existsProduct(id)) {
            throw new IllegalArgumentException("해당 id는 없습니다.");
        }
        return productDao.selectProduct(id);
    }



}


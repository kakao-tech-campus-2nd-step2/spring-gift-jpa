package gift.main.service;

import gift.main.dto.ProductDto;
import gift.main.dto.ProductRequest;
import gift.main.entity.Product;
import gift.main.handler.ProductValidator;
import gift.main.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductDao productDao;
    public List<Product> getProducts() {
        List<Product> products= productDao.selectProductAll();
        return products;
    }

    public void addProduct(ProductRequest productRequest) {
        ProductDto validProduct = ProductValidator.isValidProductDto(productRequest);
        productDao.insertProduct(validProduct);
    }

    public void deleteProduct(long id) {
        if (!productDao.existsProduct(id)) {
            throw new IllegalArgumentException("해당 id는 없습니다.");
        }
        deleteProduct(id);
    }

    public void updateProduct(long id, ProductRequest productRequest) {
        if (!productDao.existsProduct(id)) {
            throw new IllegalArgumentException("해당 id는 없습니다.");
        }
        ProductDto validProduct = ProductValidator.isValidProductDto(productRequest);
        productDao.updateProduct(id, validProduct);
    }




}

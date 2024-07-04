package gift.main.service;

import gift.main.dto.ProductDto;
import gift.main.dto.ProductRequest;
import gift.main.entity.Product;
import gift.main.repository.ProductDao;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getProducts() {
        List<Product> productList= productDao.selectProductAll();
        return productList;
    }

    public void addProduct(ProductRequest productRequest) {
        ProductDto productDto = new ProductDto(productRequest);
        productDao.insertProduct(productDto);
    }

    public void deleteProduct(long id) {

        productDao.deleteProduct(id);
    }

    public void updateProduct(long id,ProductRequest productRequest) {
        if (!productDao.existsProduct(id)) {
            throw new IllegalArgumentException("해당 id는 없습니다.");
        }
        ProductDto productDto = new ProductDto(productRequest);
        productDao.updateProduct(id, productDto);
    }

    public Product getProduct(long id) {
        if (!productDao.existsProduct(id)) {
            throw new IllegalArgumentException("해당 id는 없습니다.");
        }
        return productDao.selectProduct(id);
    }





}


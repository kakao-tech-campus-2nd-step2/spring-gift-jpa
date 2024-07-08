package gift.service;

import gift.dto.ProductRegisterRequestDto;
import gift.domain.Product;
import gift.repository.ProductDao;
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

    public ProductRegisterRequestDto getProductById(long id) {
        Product product = productDao.findById(id);
        return new ProductRegisterRequestDto(product.getName(), product.getPrice(),
            product.getImageUrl());
    }

    public Long addProduct(ProductRegisterRequestDto productDto){
        Product newProduct = new Product(productDto.getName(),productDto.getPrice(),productDto.getImageUrl());
        return productDao.insertProduct(newProduct);
    }

    public Long updateProduct(long id, ProductRegisterRequestDto productDto){
        Product updatedProduct = new Product(productDto.getName(),productDto.getPrice(),productDto.getImageUrl());
        return productDao.updateProduct(id, updatedProduct);
    }

    public Long deleteProduct(long id){
        return productDao.deleteById(id);
    }
}

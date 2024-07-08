package gift.service;

import gift.model.product.Product;
import gift.model.product.ProductListResponse;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.repository.ProductDao;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductResponse register(ProductRequest productRequest) {
        Product product = productDao.save(productRequest);
        return ProductResponse.from(product);
    }

    public ProductResponse findProduct(Long id) {
        Product product = productDao.findById(id);
        return ProductResponse.from(product);
    }

    public ProductListResponse findAllProduct() {
        List<Product> productList = productDao.findAll();
        List<ProductResponse> responseList = productList.stream().map(ProductResponse::from)
            .collect(Collectors.toList());
        ProductListResponse responses = new ProductListResponse(responseList);
        return responses;
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productDao.update(id, productRequest);
        return ProductResponse.from(product);
    }

    public void deleteProduct(Long id) {
        productDao.delete(id);
    }
}

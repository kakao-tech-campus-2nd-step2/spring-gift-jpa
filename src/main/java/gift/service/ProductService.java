package gift.service;

import gift.dto.ProductRequest;
import gift.model.Product;
import gift.repository.ProductDao;
import gift.exception.product.ProductAlreadyExistsException;
import gift.exception.product.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product makeProduct(ProductRequest request) {
        Optional<Product> optionalProduct = productDao.find(request.id());

        if (!optionalProduct.isPresent()) {
            Product product = new Product(
                    request.id(),
                    request.name(),
                    request.price(),
                    request.imageUrl()
            );
            productDao.insert(product);
            return product;
        }
        throw new ProductAlreadyExistsException("이미 해당 id의 상품이 존재합니다.");
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product getProduct(Long id) {
        Product product = productDao.find(id)
                .orElseThrow(() -> new ProductNotFoundException("해당 id의 상품이 존재하지 않습니다."));
        return product;
    }

    public Product putProduct(ProductRequest request) {
        Optional<Product> optionalProduct = productDao.find(request.id());

        if (optionalProduct.isPresent()) {
            Product updateProduct = new Product(
                    request.id(),
                    request.name(),
                    request.price(),
                    request.imageUrl()
            );
            productDao.update(request.id(), updateProduct);
            return updateProduct;
        }
        throw new ProductNotFoundException("수정하려는 해당 id의 상품이 존재하지 않습니다.");
    }

    public void deleteProduct(Long id) {
        productDao.find(id)
                .orElseThrow(() -> new ProductNotFoundException("삭제하려는 해당 id의 상품이 존재하지 않습니디."));
        productDao.delete(id);
    }
}

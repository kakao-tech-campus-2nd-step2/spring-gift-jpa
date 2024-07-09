package gift.service;

import gift.dao.ProductDao;
import gift.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final CatchError catchError = new CatchError();

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.selectAllProduct();
    }

    public Product getProductById(Long id) {
        return productDao.selectProduct(id);
    }

    public void postProduct(Product product) {
        validateProduct(product);
        productDao.insertProduct(product);
    }

    public void deleteProduct(Long id) {
        productDao.deleteProduct(id);
    }

    public void updateProduct(Long id, Product newProduct) {
        Product oldProduct = productDao.selectProduct(id);
        if(newProduct.getName() == null){
            validateProduct(newProduct);
        }

        Product updatedProduct = new Product(
                oldProduct.getId(),
                newProduct.getName() != null && !newProduct.getName().isEmpty() ? newProduct.getName() : oldProduct.getName(),
                newProduct.getPrice() != null ? newProduct.getPrice() : oldProduct.getPrice(),
                newProduct.getImageUrl() != null && !newProduct.getImageUrl().isEmpty() ? newProduct.getImageUrl() : oldProduct.getImageUrl()
        );

        productDao.updateProduct(updatedProduct);
    }

    private void validateProduct(Product product) {
        if (!catchError.isCorrectName(product.getName())) {
            throw new IllegalArgumentException("이름은 최대 15자 이내이어야 하며, 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");
        }
        if (catchError.isContainsKakao(product.getName())) {
            throw new IllegalArgumentException("\"카카오\"는 MD와 협의 후에 사용 가능합니다.");
        }
    }
}
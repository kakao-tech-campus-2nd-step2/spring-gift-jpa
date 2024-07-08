package gift.service;

import gift.constants.ErrorMessage;
import gift.dto.Product;
import gift.repository.ProductDao;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * 새 상품을 저장. 이미 존재하면 IllegalArgumentException
     *
     * @param product
     */
    public void addProduct(Product product) {
        productDao.selectOneProduct(product.id())
            .ifPresent(v -> {
                throw new IllegalArgumentException(ErrorMessage.ID_ALREADY_EXISTS_MSG);
            });
        productDao.insertNewProduct(product);
    }

    /**
     * 상품 정보 수정. 존재하지 않는 상품이면 NoSuchElementException
     *
     * @param product
     */
    public void editProduct(Product product) {
        productDao.selectOneProduct(product.id())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        productDao.updateProduct(product);
    }

    /**
     * 상품 삭제. 존재하지 않는 상품이면 NoSuchElementException
     *
     * @param id
     */
    public void deleteProduct(Long id) {
        productDao.selectOneProduct(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        productDao.deleteProduct(id);
    }

    /**
     * 모든 상품 리스트 반환
     *
     * @return 상품 List
     */
    public List<Product> getAllProducts() {
        return productDao.selectProducts();
    }

    /**
     * id에 해당하는 상품 반환
     *
     * @param id
     * @return Product 객체
     */
    public Product getProduct(Long id) {
        return productDao.selectOneProduct(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
    }
}

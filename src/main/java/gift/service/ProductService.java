package gift.service;

import gift.constants.ErrorMessage;
import gift.dto.Product;
import gift.repository.ProductJpaDao;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaDao productJpaDao;

    public ProductService(ProductJpaDao productJpaDao) {
        this.productJpaDao = productJpaDao;
    }

    /**
     * 새 상품을 저장. 이미 존재하면 IllegalArgumentException
     *
     * @param product
     */
    public void addProduct(Product product) {
        productJpaDao.findById(product.getId())
            .ifPresent(v -> {
                throw new IllegalArgumentException(ErrorMessage.ID_ALREADY_EXISTS_MSG);
            });
        productJpaDao.save(product);
    }

    /**
     * 상품 정보 수정. 존재하지 않는 상품이면 NoSuchElementException
     *
     * @param product
     */
    public void editProduct(Product product) {
        productJpaDao.findById(product.getId())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        productJpaDao.save(product);
    }

    /**
     * 상품 삭제. 존재하지 않는 상품이면 NoSuchElementException
     *
     * @param id
     */
    public void deleteProduct(Long id) {
        productJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
        productJpaDao.deleteById(id);
    }

    /**
     * 모든 상품 리스트 반환
     *
     * @return 상품 List
     */
    public List<Product> getAllProducts() {
        return productJpaDao.findAll();
    }

    /**
     * id에 해당하는 상품 반환
     *
     * @param id
     * @return Product 객체
     */
    public Product getProduct(Long id) {
        return productJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
    }
}

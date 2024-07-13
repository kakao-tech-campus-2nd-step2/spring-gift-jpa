package gift.service;
import gift.dao.ProductDao;
import gift.entity.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
  private ProductDao productDao;

  @Autowired
  public ProductService(ProductDao productDao) {
    this.productDao = productDao;
  }

  public List<Product> findAll() {
    try {
      return productDao.findAll();
    } catch (Exception e) {
      throw new RuntimeException("모든 상품을 조회하는 중에 오류가 발생했습니다.", e);
    }
  }

  public Product getProductById(long id) {
    try {
      return productDao.findById(id);
    } catch (Exception e) {
      throw new RuntimeException("ID가 " + id + "인 상품을 조회하는 중에 오류가 발생했습니다.", e);
    }
  }

  public Product addProduct(Product product) {
    try {
      productDao.save(product);
      return product;
    } catch (Exception e) {
      throw new RuntimeException("상품을 추가하는 중에 오류가 발생했습니다.", e);
    }
  }

  public Product updateProduct(Product product) {
    try {
      productDao.update(product);
      return product;
    } catch (Exception e) {
      throw new RuntimeException("상품을 업데이트하는 중에 오류가 발생했습니다.", e);
    }
  }

  public void deleteProduct(long id) {
    try {
      productDao.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("ID가 " + id + "인 상품을 삭제하는 중에 오류가 발생했습니다.", e);
    }
  }
}


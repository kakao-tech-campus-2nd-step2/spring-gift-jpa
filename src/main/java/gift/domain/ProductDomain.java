package gift.domain;

import gift.controller.CatchError;
import gift.dao.ProductDao;
import gift.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Component
public class ProductDomain {
    private final ProductDao productDao;
    private final CatchError catchError = new CatchError();

    public ProductDomain(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts(){
        return productDao.selectAllProduct();
    }

    public Product getProductById(Long id){
        return productDao.selectProduct(id);
    }

    public void postProduct(Product product){
        if (!catchError.isCorrectName(product.getName())) {
            throw new IllegalArgumentException("이름은 15자 이내, 특수문자는 (),[],+,-,&,/,_ 만 사용 가능합니다.");
        }
        if (catchError.isContainsKakao(product.getName())) {
            throw new IllegalArgumentException("\"카카오\"는 MD와 협의 후에 사용 가능합니다.");
        }
        // Product 객체 생성 시 id 제외
        productDao.insertProduct(product);
    }

    public void deleteProduct(Long id){
        productDao.deleteProduct(id);
    }

    public void updateProduct(Long id, Product product){
        Product oldProduct = productDao.selectProduct(id);

        if (!catchError.isCorrectName(product.getName())) {
            throw new IllegalArgumentException("이름은 15자 이내, 특수문자는 (),[],+,-,&,/,_ 만 사용 가능합니다.");
        }
        if (catchError.isContainsKakao(product.getName())) {
            throw new IllegalArgumentException("\"카카오\"는 MD와 협의 후에 사용 가능합니다.");
        }

        Product updatedProduct = new Product(
                oldProduct.getId(),
                product.getName() != null && !product.getName().isEmpty() ? product.getName() : oldProduct.getName(),
                product.getPrice() != null ? product.getPrice() : oldProduct.getPrice(),
                product.getImageUrl() != null && !product.getImageUrl().isEmpty() ? product.getImageUrl() : oldProduct.getImageUrl()
        );

        productDao.updateProduct(updatedProduct);
    }

    // 이름 오류 발생시 이유 리턴
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "errorPage";
    }
}

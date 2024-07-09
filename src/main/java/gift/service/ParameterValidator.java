package gift.service;

import gift.dao.ProductDao;
import gift.dao.UserDao;
import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.entity.User;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.exception.BadRequestExceptions.DataCorruptionException;
import gift.exception.BadRequestExceptions.InvalidIdException;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterValidator {
    private final ProductDao productDao;

    @Autowired
    public ParameterValidator(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void validateParameter(Integer id, ProductDTO productDTO) throws BadRequestException {
        if (!Objects.equals(productDTO.id(), id)) {
            throw new InvalidIdException("올바르지 않은 id입니다.");
        }
    }
    public void validateParameter(Product product) throws BadRequestException {
        Product productInDb = productDao.selectOneProduct(product.getId());
        if(!product.equals(productInDb))
            throw new DataCorruptionException("클라이언트의 상품 데이터와 DB의 상품 데이터가 일치하지 않습니다.");
    }

    public void validateParameter(Map<String, Object> userAndWishLists, User user) throws BadRequestException {
        User userInDb = (User) userAndWishLists.get("user");
        if(!user.equals(userInDb))
            throw new DataCorruptionException("클라이언트의 유저 데이터와 DB의 유저 데이터가 일치하지 않습니다.");
    }

    public void validateParameter(Map<String, Object> userAndWishLists, User user, Product product) throws BadRequestException {
        validateParameter(userAndWishLists, user);
        validateParameter(product);
    }
}

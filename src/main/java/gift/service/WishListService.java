package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import gift.dao.ProductDao;
import gift.dao.UserDao;
import gift.dto.ProductDTO;
import gift.dto.UserDTO;
import gift.dto.WishListDTO;
import gift.entity.Product;
import gift.entity.User;
import gift.entity.WishList;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.exception.BadRequestExceptions.NoSuchProductIdException;
import gift.exception.InternalServerExceptions.InternalServerException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final UserDao userDao;
    private final ProductDao productDao;
    private final ParameterValidator parameterValidator;

    @Autowired
    public WishListService(UserDao userDao, ProductDao productDao,
            ParameterValidator parameterValidator) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.parameterValidator = parameterValidator;
    }

    public WishListDTO getWishList(UserDTO userDTO)
            throws InternalServerException {
        try {
            WishList wishList = getWishListInDb(new User(userDTO));
            return wishList.convertToDTO();
        } catch (JsonProcessingException e) {
            throw new InternalServerException("예상치 못한 오류입니다. 관리자에게 연락 주시기 바랍니다.");
        }
    }

    public void addWishListProduct(UserDTO userDTO, ProductDTO productDTO)
            throws InternalServerException {
        User user = new User(userDTO);
        Product product = new Product(productDTO);
        try {
            WishList wishList = getWishListInDb(user, product);
            wishList.addProduct(product);
            userDao.saveWishList(user, wishList);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("예상치 못한 오류입니다. 관리자에게 연락 주시기 바랍니다.");
        }

    }

    public void removeWishListProduct(UserDTO userDTO, Integer id)
            throws InternalServerException, JsonProcessingException {
        User user = new User(userDTO);
        try {
            Product product = productDao.selectOneProduct(id);
            WishList wishList = getWishListInDb(user);
            if (!wishList.removeProduct(product)) {
                throw new NoSuchProductIdException("id가 %d인 상품은 존재하지 않습니다.".formatted(id));
            }
            userDao.saveWishList(user, wishList);
        } catch (InternalServerException e) {
            throw new InternalServerException("예상치 못한 오류입니다. 관리자에게 연락 주시기 바랍니다.");
        } catch(EmptyResultDataAccessException e){ //제품 목록에는 없는데 위시리스트에 있는 경우
            WishList wishList = getWishListInDb(user);
            if(!wishList.removeProduct(id))
                throw new NoSuchProductIdException("id가 %d인 상품은 존재하지 않습니다.".formatted(id));
        }
    }

    public void setWishListNumber(UserDTO userDTO, ProductDTO productDTO, Integer quantity)
            throws InternalServerException {
        User user = new User(userDTO);
        Product product = new Product(productDTO);
        try {
            WishList wishList = getWishListInDb(user, product);
            wishList.setNumbers(product, quantity);
            userDao.saveWishList(user, wishList);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("예상치 못한 오류입니다. 관리자에게 연락 주시기 바랍니다.");
        }
    }

    private WishList getWishListInDb(User user)
            throws JsonProcessingException, BadRequestException {
        Map<String, Object> userAndWishLists = userDao.getWishLists(user.getEmail());
        if(userAndWishLists == null)
            return new WishList();
        parameterValidator.validateParameter(userAndWishLists, user);
        return (WishList) userAndWishLists.get("wishList");
    }

    private WishList getWishListInDb(User user, Product product)
            throws JsonProcessingException, BadRequestException {
        Map<String, Object> userAndWishLists = userDao.getWishLists(user.getEmail());
        if(userAndWishLists == null)
            return new WishList();
        parameterValidator.validateParameter(userAndWishLists, user, product);
        return (WishList) userAndWishLists.get("wishList");
    }
}

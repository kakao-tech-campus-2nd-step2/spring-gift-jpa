package gift.service;

import gift.common.auth.JwtTokenProvider;
import gift.common.exception.ExistUserException;
import gift.model.product.Product;
import gift.model.product.ProductListResponse;
import gift.model.product.ProductResponse;
import gift.model.user.User;
import gift.model.user.UserRequest;
import gift.model.user.UserResponse;
import gift.repository.UserDao;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserDao userDao, JwtTokenProvider jwtTokenProvider) {
        this.userDao = userDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UserResponse register(UserRequest userRequest) {
        if (userDao.existsByEmail(userRequest.email())) {
            throw new ExistUserException();
        }
        User user = userDao.save(userRequest);
        return UserResponse.from(user);
    }

    public String login(UserRequest userRequest) {
        User user = userDao.findByEmail(userRequest.email());
        return jwtTokenProvider.createToken(user.getEmail());
    }

    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public ProductListResponse findWishList(Long userId) {
        List<Product> wishList = userDao.findWishList(userId);
        List<ProductResponse> wishResponses = wishList.stream().map(ProductResponse::from)
            .collect(Collectors.toList());
        ProductListResponse responses = new ProductListResponse(wishResponses);
        return responses;
    }

    public void addWistList(Long userId, Long productId, int count) {
        userDao.registerWishList(userId, productId, count);
    }

    public void deleteWishList(Long userId, Long productId, int count) {
        userDao.delete(userId, productId, count);
    }
}

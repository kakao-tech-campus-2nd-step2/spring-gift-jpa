package gift.service;

import gift.converter.WishListConverter;
import gift.dto.WishListDTO;
import gift.jwt.JwtInterceptor;
import gift.model.Product;
import gift.model.User;
import gift.model.WishList;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class WishListService {
    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public WishListService(WishListRepository wishListRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public WishListDTO getWishListByUser(String email) {
        User user = userRepository.findByEmail(email);
        logger.info("user123: " + user);

        WishList wishList = wishListRepository.findByUser(user)
            .orElseGet(() -> {
                WishList newWishList = new WishList(user.getId(), user, new ArrayList<>());
                wishListRepository.save(newWishList);
                logger.info("list123: " + newWishList);
                return newWishList;
            });
        if (wishList.getUser() != null) {
            wishList.getUser().getEmail(); // 지연 로딩된 user 엔티티 초기화
        }

        return WishListConverter.convertToDTO(wishList);
    }

    public void addProductToWishList(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        WishList wishList = wishListRepository.findByUser(user)
            .orElseGet(() -> new WishList(null, user, new ArrayList<>()));

        List<Product> products = wishList.getProducts();
        if (!products.contains(product)) {
            products.add(product);
            wishListRepository.save(wishList);
        }
    }

    @Transactional
    public void removeProductFromWishList(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        WishList wishList = wishListRepository.findByUser(user)
            .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for user: " + email));

        List<Product> products = wishList.getProducts();
        if (products.contains(product)) {
            products.remove(product);
            logger.info("list12345: " + wishList.getProducts());
            wishListRepository.save(wishList);
        }
    }
}
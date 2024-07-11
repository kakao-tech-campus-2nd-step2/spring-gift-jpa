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
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class WishListService {
    private static final Logger logger = LoggerFactory.getLogger(WishListService.class);
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<WishListDTO> getWishListByUser(String email) {
        User user = userRepository.findByEmail(email);
        List<WishList> wishLists = wishListRepository.findByUser(user);
        return wishLists.stream()
            .map(WishListConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public void addProductToWishList(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        WishList newWishList = new WishList(null, user, product);
        wishListRepository.save(newWishList);
    }

    @Transactional
    public void removeProductFromWishList(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        WishList wishList = wishListRepository.findByUserAndProduct(user, product)
            .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for user: " + email));

        wishListRepository.delete(wishList);
    }
}
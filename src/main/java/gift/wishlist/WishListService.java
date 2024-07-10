package gift.wishlist;

import gift.auth.JwtUtil;
import gift.product.Product;
import gift.product.ProductDTO;
import gift.product.ProductService;
import gift.user.User;
import gift.user.UserDTO;
import gift.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishListService {
    private static final Logger logger = LoggerFactory.getLogger(WishListService.class);
    private final WishListRepository wishListRepository;
    private final ProductService productService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public WishListService(WishListRepository wishListRepository, ProductService productService,
        UserService userService,
        JwtUtil jwtUtil) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public List<WishListDTO> getWishListsByUserId(long id) {
        return wishListRepository.findAllByUserId(id).stream()
            .map(WishListDTO::fromWishList)
            .toList();
    }

    public void extractEmailFromTokenAndValidate(HttpServletRequest request, String email) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        String token = authHeader.substring(7);
        String tokenEmail;
        tokenEmail = jwtUtil.extractEmail(token);
        if (!email.equals(tokenEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이메일이 토큰과 일치하지 않습니다.");
        }
    }

    public void addWishList(WishListDTO wishList, String email) throws NotFoundException {
        UserDTO userDTO = userService.findUserByEmail(email);
        User user = userDTO.toUser();
        if (wishListRepository.existsByUserIdAndProductId(user.getId(),
            wishList.getProductId())) {
            throw new IllegalArgumentException(email + "의 위시리스트에 존재하는 상품입니다.");
        }
        logger.info("id: {}", wishList.getProductId());
        ProductDTO productDTO = productService.getProductById(wishList.getProductId());
        Product product = productDTO.toProduct();
        WishList wishList1 = new WishList(user, product, wishList.getNum());
        wishListRepository.save(wishList1);
    }

    public void updateWishList(long userId, long productId, int num) throws NotFoundException {
        WishList wishList = wishListRepository.findByUserIdAndProductId(userId, productId);
        if (!wishListRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new IllegalArgumentException(
                userService.findById(userId).email() + "의 위시리스트에는 " + productService.getProductById(productId).getName()
                    + " 상품이 존재하지 않습니다.");
        }
        wishList.update(num);
        wishListRepository.save(wishList);
    }

    public void deleteWishList(long userId, long productId) throws NotFoundException {
        if (!wishListRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new IllegalArgumentException(
                userService.findById(userId).email() + "의 위시리스트에는 " + productService.getProductById(productId).getName()
                    + " 상품이 존재하지 않습니다.");
        }
        wishListRepository.deleteByUserIdAndProductId(userId, productId);
    }
}

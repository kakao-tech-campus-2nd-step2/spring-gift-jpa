package gift.wishlist;

import gift.auth.JwtUtil;
import gift.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductService productService;
    private final JwtUtil jwtUtil;

    @Autowired
    public WishListService(WishListRepository wishListRepository, ProductService productService,
        JwtUtil jwtUtil) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    }

    public List<WishListDTO> getWishListsByEmail(String email) {
        return wishListRepository.findAllByEmail(email).stream()
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

    public void addWishList(WishListDTO wishList) {
        if (wishListRepository.existsByEmailAndProductId(wishList.getEmail(),
            wishList.getProductId())) {
            throw new IllegalArgumentException(wishList.getEmail() + "의 위시리스트에 존재하는 상품입니다.");
        }
        wishListRepository.save(wishList.toWishList());
    }

    public void updateWishList(String email, long productId, int num) throws NotFoundException {
        WishList wishList = wishListRepository.findByEmailAndProductId(email, productId);
        if (!existsByEmailAndProductId(email, productId)) {
            throw new IllegalArgumentException(
                email + "의 위시리스트에는 " + productService.getProductById(productId).getName()
                    + " 상품이 존재하지 않습니다.");
        }
        wishList.update(email, productId, num);
        wishListRepository.save(wishList);
    }

    public Boolean existsByEmailAndProductId(String email, long productId) {
        return wishListRepository.existsByEmailAndProductId(email, productId);
    }

    public void deleteWishList(String email, long productId) throws NotFoundException {
        if (!existsByEmailAndProductId(email, productId)) {
            throw new IllegalArgumentException(
                email + "의 위시리스트에는 " + productService.getProductById(productId).getName()
                    + " 상품이 존재하지 않습니다.");
        }
        wishListRepository.deleteByEmailAndProductId(email, productId);
    }
}

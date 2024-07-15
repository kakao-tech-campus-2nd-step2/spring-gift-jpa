package gift.service;

import gift.domain.Product;
import gift.domain.User;
import gift.domain.Wish;
import gift.dto.Wishlist;
import gift.dto.Wishlist.Request;
import gift.dto.Wishlist.Response;
import gift.exception.ProductException;
import gift.exception.UserErrorCode;
import gift.exception.UserException;
import gift.exception.UserException.BadRequest;
import gift.exception.UserException.BadToken;
import gift.exception.WishErrorCode;
import gift.exception.WishException;
import gift.repository.ProductJdbcRepository;
import gift.repository.ProductJpaRepository;
import gift.repository.UserJpaRepository;
import gift.repository.UserRepository;
import gift.repository.WishJpaRepository;
import gift.repository.WishlistRepository;
import gift.util.JwtTokenProvider;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final UserJpaRepository userRepository;
    private final WishJpaRepository wishlistRepository;
    private final ProductJpaRepository productRepository;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public WishlistService(UserJpaRepository userRepository, WishJpaRepository wishlistRepository,
        ProductJpaRepository productRepository, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.tokenProvider = tokenProvider;
    }

    public long findUserId(String accessToken) {
        // 토큰 검증하기
        tokenProvider.validateToken(accessToken);
        return userRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new UserException.BadToken(UserErrorCode.INVALID_TOKEN))
            .getId();
    }

    public List<Wishlist.Response> getAllWishlistItems(long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BadRequest(UserErrorCode.ID_NOT_EXISTS));
        List<Wish> wishList = wishlistRepository.findByUser(user);

        return wishList.stream()
            .map(wish -> new Wishlist.Response(wish.getProductName(), wish.getQuantity()))
            .collect(Collectors.toList());
    }

    public Wishlist.Response addItemToWishlist(String accessToken, Request request) {
        User user = userRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new BadToken(UserErrorCode.INVALID_TOKEN));

        Wish wish = new Wish();
        wish.setProductName(request.getProductName());
        wish.setQuantity(request.getQuantity());
        wish.setUser(user);

        user.addWish(wish);
        return new Wishlist.Response(request.getProductName(), request.getQuantity());
    }

    public Wishlist.Response deleteItemFromWishlist(String accessToken, Request request) {
        User user = userRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new BadToken(UserErrorCode.INVALID_TOKEN));

        Wish wish = wishlistRepository.findByProductName(
            request.getProductName())
            .orElseThrow(() -> new WishException(WishErrorCode.PRODUCT_NAME_NOT_EXISTS));

        user.removeWish(wish);
        userRepository.save(user);
        return new Wishlist.Response(request.getProductName(), request.getQuantity());
    }
}

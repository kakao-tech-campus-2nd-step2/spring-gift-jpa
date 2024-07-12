package gift.service;

import gift.dto.Wishlist;
import gift.dto.Wishlist.Request;
import gift.dto.Wishlist.Response;
import gift.repository.ProductJdbcRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;
import gift.util.JwtTokenProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;
    private final ProductJdbcRepository productRepository;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public WishlistService(UserRepository userRepository, WishlistRepository wishlistRepository,
        ProductJdbcRepository productRepository, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.tokenProvider = tokenProvider;
    }

    public long findUserId(String accessToken) {
        // 토큰 검증하기
        tokenProvider.validateToken(accessToken);
        return userRepository.findIdByAccessToken(accessToken);
    }

    public List<Wishlist.Response> getAllWishlistItems(long userId) {
        List<Response> items = wishlistRepository.findAllWishlistItems(userId);
        return items;
    }

    public Wishlist.Response addItemToWishlist(long userId, Request request) {
        long productId = productRepository.findIdByName(request.getProductName());

        wishlistRepository.save(userId, productId, request);
        return new Wishlist.Response(request.getProductName(), request.getQuantity());
    }

    public Wishlist.Response deleteItemFromWishlist(long userId, Request request) {
        long productId = productRepository.findIdByName(request.getProductName());

        wishlistRepository.deleteByProductId(userId, productId);
        return new Wishlist.Response(request.getProductName(), request.getQuantity());
    }
}

package gift.service;

import gift.dto.WishListDto;
import gift.entity.WishList;

import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import gift.util.JwtUtil;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    
    private WishListRepository wishListRepository;
    private ProductRepository productRepository;
    private JwtUtil jwtUtil;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository, JwtUtil jwtUtil) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<WishListDto> findWishListById(String token) {

        long memberId = (long)jwtUtil.extractAllClaims(token).get("id");
        List<WishList> wishlist = wishListRepository.findProductIdsByMemberId(memberId);
        return wishlist.stream()
        .map(WishListDto::fromEntity)
        .collect(Collectors.toList());
    }

    public void addWishList(String token, long productId) {

        long memberId = (long)jwtUtil.extractAllClaims(token).get("id");
        productRepository.findById(productId);
        wishListRepository.save(new WishList(memberId, productId));

    }

    public void deleteWishList(String token, long productId) {
        long memberId = (long)jwtUtil.extractAllClaims(token).get("id");
        wishListRepository.deleteById(wishListRepository.findId(memberId, productId));;
    }
}

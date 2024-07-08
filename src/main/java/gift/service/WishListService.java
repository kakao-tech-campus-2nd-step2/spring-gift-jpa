package gift.service;

import gift.dao.WishListDao;
import gift.dto.WishListDto;
import gift.repository.ProductRepository;
import gift.util.JwtUtil;
import gift.domain.WishList;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    
    private WishListDao wishListDao;
    private ProductRepository productRepository;
    private JwtUtil jwtUtil;

    public WishListService(WishListDao wishListDao, ProductRepository productRepository, JwtUtil jwtUtil){
        this.wishListDao =wishListDao;
        this.productRepository = productRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<WishListDto> findWishListById(String token){

        long userId = (long)jwtUtil.extractAllClaims(token).get("id");
        List<WishList> wishlist = wishListDao.findWishListById(userId);
        return wishlist.stream()
        .map(WishListDto::fromEntity)
        .collect(Collectors.toList());
    }

    public void addWishList(String token, long productId){

        long userId = (long)jwtUtil.extractAllClaims(token).get("id");
        productRepository.findById(productId);
        wishListDao.insertWishList(productId, userId);

    }

    public void deleteWishList(String token, long productId){

        long userId = (long)jwtUtil.extractAllClaims(token).get("id");
        wishListDao.deleteWishList(productId, userId);
    }
}

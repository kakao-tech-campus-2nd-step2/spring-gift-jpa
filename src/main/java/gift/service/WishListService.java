package gift.service;

import gift.dao.ProductDao;
import gift.dao.WishListDao;
import gift.dto.WishListDto;
import gift.exception.CustomException;
import gift.util.JwtUtil;
import gift.domain.WishList;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import java.util.List;


public class WishListService {
    
    private WishListDao wishListDao;
    private ProductDao productDao;
    private JwtUtil jwtUtil;

    public WishListService(WishListDao wishListDao, ProductDao productDao, JwtUtil jwtUtil){
        this.wishListDao =wishListDao;
        this.productDao = productDao;
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
        productDao.findOne(productId).orElseThrow(() -> new CustomException("Product with id " + productId + " not found", HttpStatus.NOT_FOUND));
        wishListDao.insertWishList(productId, userId);

    }

    public void deleteWishList(String token, long productId){

        long userId = (long)jwtUtil.extractAllClaims(token).get("id");
        wishListDao.deleteWishList(productId, userId);
    }
}

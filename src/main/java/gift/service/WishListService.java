package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.Util.JWTUtil;
import gift.dto.WishDTO;
import gift.entity.WishList;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.UnAuthException;
import gift.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    ObjectMapper objectMapper;

    public void add(String token, int productId) {
        int tokenUserId = JWTUtil.getIdFromToken(token);
        if(!JWTUtil.isValidToken(token))
            throw new UnAuthException("로그인 만료");
        WishList wishList = new WishList(tokenUserId,productId);

        if(wishListRepository.isExistWishList(wishList))
           throw new BadRequestException("이미 추가된 물품입니다.");
        wishListRepository.save(wishList);
    }

    public String getWishList(String token) throws JsonProcessingException {
        int tokenUserId = JWTUtil.getIdFromToken(token);
        if(!JWTUtil.isValidToken(token))
            throw new UnAuthException("로그인 만료");
        List<WishDTO.wishListProduct> wishlist = wishListRepository.getWishList(tokenUserId);
        return objectMapper.writeValueAsString(wishlist);
    }

    public void deleteWishList(String token, int productId) {
        if(!JWTUtil.isValidToken(token))
            throw new UnAuthException("로그인 만료");
        int tokenUserId = JWTUtil.getIdFromToken(token);
        wishListRepository.deleteWishList(tokenUserId,productId);
    }
}

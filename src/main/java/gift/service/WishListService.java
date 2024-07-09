package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.Util.JWTUtil;
import gift.compositeKey.WishListId;
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
        WishListId wishListId = new WishListId(tokenUserId,productId);

        if(wishListRepository.findById(wishListId).isEmpty())
           throw new BadRequestException("이미 추가된 물품입니다.");
        wishListRepository.save(new WishList(wishListId));
    }

    public String getWishList(String token) throws JsonProcessingException {
        int tokenUserId = JWTUtil.getIdFromToken(token);
        if(!JWTUtil.isValidToken(token))
            throw new UnAuthException("로그인 만료");
        List<WishDTO.wishListProduct> wishlist = wishListRepository.findByUserId(tokenUserId);
        return objectMapper.writeValueAsString(wishlist);
    }

    public void deleteWishList(String token, int productId) {
        if(!JWTUtil.isValidToken(token))
            throw new UnAuthException("로그인 만료");
        int tokenUserId = JWTUtil.getIdFromToken(token);
        WishListId wishListId = new WishListId(tokenUserId,productId);
        wishListRepository.deleteById(wishListId);
    }
}

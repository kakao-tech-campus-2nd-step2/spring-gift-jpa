package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.Util.JWTUtil;
import gift.entity.Product;
import gift.entity.User;
import gift.entity.compositeKey.WishListId;
import gift.dto.WishDTO;
import gift.entity.WishList;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.exception.exception.UnAuthException;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;

    public void add(String token, int productId) {
        int tokenUserId = JWTUtil.getIdFromToken(token);
        if(!JWTUtil.isValidToken(token))
            throw new UnAuthException("로그인 만료");

        WishListId wishListId = new WishListId(tokenUserId,productId);
        if(userRepository.findById(tokenUserId).isEmpty())
            throw new NotFoundException("인증이 잘못됨");
        if(productRepository.findById(productId).isEmpty())
            throw new NotFoundException("해당 물건이없습니다.");
        if(wishListRepository.findById(wishListId).isPresent())
           throw new BadRequestException("이미 추가된 물품입니다.");

        User user = userRepository.findById(tokenUserId).get();
        Product product = productRepository.findById(productId).get();
        WishList wishlist = new WishList(wishListId);
        wishListRepository.save(wishlist);
        user.addWishlist(wishlist);
        product.addWishlist(wishlist);

    }

    public String getWishList(String token) throws JsonProcessingException {
        int tokenUserId = JWTUtil.getIdFromToken(token);
        if(!JWTUtil.isValidToken(token))
            throw new UnAuthException("로그인 만료");
        List<WishDTO.wishListProduct> wishlist = wishListRepository.findByUserId(tokenUserId).stream()
                .map(array-> new WishDTO.wishListProduct(
                        (String) array[0],
                        (Integer) array[1],
                        (String) array[2]
                )).collect(Collectors.toList());
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

package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import gift.Util.JWTUtil;
import gift.dto.wishlist.WishProductDTO;
import gift.entity.Product;
import gift.entity.User;
import gift.entity.WishList;
import gift.entity.compositeKey.WishListId;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.exception.exception.UnAuthException;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    public void add(String token, int productId) {
        int tokenUserId = jwtUtil.getUserIdFromToken(token);
        if(!jwtUtil.validateToken(token))
            throw new UnAuthException("로그인 만료");
        WishListId wishListId = new WishListId(tokenUserId,productId);
        if(productRepository.findById(productId).isEmpty())
            throw new NotFoundException("해당 물건이없습니다.");
        if(wishListRepository.findById(wishListId).isPresent())
           throw new BadRequestException("이미 추가된 물품입니다.");

        WishList wishList = new WishList(wishListId);
        Product product = productRepository.findById(productId).get();
        User user = userRepository.findById(tokenUserId).get();
        wishList.setProduct(product);
        wishList.setUser(user);
        wishList = wishListRepository.save(wishList);
        product.addWishlist(wishList);
        user.addWishlist(wishList);
    }

    public Page<WishProductDTO> getWishList(String token, Pageable pageable) throws JsonProcessingException {
        int tokenUserId = jwtUtil.getUserIdFromToken(token);
        if(!jwtUtil.validateToken(token))
            throw new UnAuthException("로그인 만료");
        Page<Object[]> result = wishListRepository.findByUserId(tokenUserId,pageable);
        return result.map(this::convertToWishListProduct);
    }

    private WishProductDTO convertToWishListProduct(Object[] array) {
        return new WishProductDTO(
                (String) array[0],
                (Integer) array[1],
                (String) array[2]
        );
    }

    public void deleteWishList(String token, int productId) {
        if(!jwtUtil.validateToken(token))
            throw new UnAuthException("로그인 만료");
        int tokenUserId = jwtUtil.getUserIdFromToken(token);
        WishListId wishListId = new WishListId(tokenUserId,productId);
        wishListRepository.deleteById(wishListId);
    }
}

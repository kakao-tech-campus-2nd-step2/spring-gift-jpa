package gift.service;

import gift.DTO.Product.ProductRequest;
import gift.DTO.Product.ProductResponse;
import gift.DTO.User.UserRequest;
import gift.DTO.User.UserResponse;
import gift.DTO.Wish.WishProductRequest;
import gift.DTO.Wish.WishProductResponse;
import gift.domain.Product;
import gift.domain.User;
import gift.domain.WishProduct;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishListService(
            WishListRepository wishListRepository, ProductRepository productRepository, UserRepository userRepository
    ){
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    /*
     * 특정 상품을 위시리스트에 추가하는 로직
     */
    public void addWishList(WishProductRequest wishProductRequest){
        UserResponse userRes = wishProductRequest.getUser();
        ProductResponse productRes = wishProductRequest.getProduct();

        User user = userRepository.findByUserId(userRes.getUserId());
        Product product = productRepository.findById(productRes.getId()).orElseThrow(NullPointerException::new);

        if(wishListRepository.existsByUserIdAndProductId(user.getId(), product.getId())){
            WishProduct wishProduct = wishListRepository.findByUserIdAndProductId(user.getId(), product.getId());
            wishProduct.changeCount(wishProduct.getCount() + 1);

            wishListRepository.save(wishProduct);
            return;
        }

        WishProduct wishProduct = new WishProduct(user, product);
        wishListRepository.save(wishProduct);
    }
    /*
     * 특정 유저의 위시리스트를 반환하는 로직
     */
    public List<WishProductResponse> loadWishList(String userId){
        List<WishProductResponse> list = new ArrayList<>();

        User byUserId = userRepository.findByUserId(userId);

        List<WishProduct> wishes = wishListRepository.findByUserId(byUserId.getId());
        for (WishProduct wishProduct : wishes) {
            User userEntity = wishProduct.getUser();
            Product productEntity = wishProduct.getProduct();

            UserResponse user = new UserResponse(
                    userEntity.getId(),
                    userEntity.getUserId(),
                    userEntity.getEmail(),
                    userEntity.getPassword()
            );
            ProductResponse product = new ProductResponse(
                    productEntity.getId(),
                    productEntity.getName(),
                    productEntity.getPrice(),
                    productEntity.getImageUrl()
            );

            WishProductResponse wishProductResponse = new WishProductResponse(
                    wishProduct.getId(),
                    user,
                    product,
                    wishProduct.getCount()
            );
            list.add(wishProductResponse);
        }

        return list;
    }
    /*
     * 특정 유저의 특정 위시리스트 물품의 수량을 변경하는 로직
     */
    public void updateWishProduct(Long userId, Long productId, int count){
        WishProduct wish = wishListRepository.findByUserIdAndProductId(userId, productId);
        wish.changeCount(count);
        wishListRepository.save(wish);
    }
    /*
     * 특정 유저의 특정 위시리스트 물품을 삭제하는 로직
     */
    @Transactional
    public void deleteWishProduct(Long userId, Long productId){
        wishListRepository.deleteByUserIdAndProductId(userId, productId);
    }

}

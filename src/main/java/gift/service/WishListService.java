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
import java.util.Optional;

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
     * 특정 유저의 위시리스트를 반환하는 로직
     */
    public List<WishProductResponse> loadWishList(Long id){
        List<WishProductResponse> list = new ArrayList<>();

        List<WishProduct> wishes = wishListRepository.findByUserId(id);
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
     * 특정 상품을 위시리스트에 추가하는 로직
     */
    @Transactional
    public void addWishList(WishProductRequest wishProductRequest){
        Long id = wishProductRequest.getUser().getId();
        Long productId = wishProductRequest.getProduct().getId();

        if(wishListRepository.existsByUserIdAndProductId(id, productId)){
            WishProduct wishProduct = wishListRepository.findByUserIdAndProductId(id, productId);
            wishProduct.changeCount(wishProduct.getCount() + 1);

            return;
        }

        User byId = userRepository.findById(id).orElseThrow(NullPointerException::new);
        Product byProductId = productRepository.findById(productId).orElseThrow(NullPointerException::new);
        WishProduct wishProduct = new WishProduct(byId, byProductId);
        wishListRepository.save(wishProduct);
    }
    /*
     * 특정 유저의 특정 위시리스트 물품의 수량을 변경하는 로직
     */
    @Transactional
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
        WishProduct wish = wishListRepository.findByUserIdAndProductId(userId, productId);
        if(wish.getCount() == 1) {
            wishListRepository.deleteByUserIdAndProductId(userId, productId);
            return;
        }
        wish.changeCount(wish.getCount() - 1);
    }

}

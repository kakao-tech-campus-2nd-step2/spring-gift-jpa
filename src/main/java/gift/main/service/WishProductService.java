package gift.main.service;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.dto.UserVo;
import gift.main.dto.WishProductResponce;
import gift.main.entity.Product;
import gift.main.entity.User;
import gift.main.entity.WishProduct;
import gift.main.repository.ProductRepository;
import gift.main.repository.UserRepository;
import gift.main.repository.WishProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishProductService {

    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishProductService(WishProductRepository wishProductRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public Page<WishProductResponce> getWishProductPage(UserVo sessionUser,Pageable pageable) {
        Page<WishProductResponce> wishProductResponcePage = wishProductRepository.findAllByUserId(sessionUser.getId(), pageable)
                .map(wishProduct -> new WishProductResponce(wishProduct));

        return wishProductResponcePage;

    }

    public void addWishlistProduct(Long productId, UserVo sessionUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (wishProductRepository.existsByProductIdAndUserId(productId, user.getId())) {
            throw new CustomException(ErrorCode.ALREADY_EXISTING_WISH_LIST)
                    ;
        }
        wishProductRepository.save(new WishProduct(product, user));
    }


    public void deleteProducts(Long productId, UserVo sessionUserVo) {
        wishProductRepository.deleteByProductIdAndUserId(productId, sessionUserVo.getId());
    }

}

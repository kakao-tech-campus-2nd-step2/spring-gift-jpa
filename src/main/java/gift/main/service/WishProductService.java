package gift.main.service;

import gift.main.dto.UserVo;
import gift.main.entity.WishProduct;
import gift.main.repository.WishProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishProductService {

    private final WishProductRepository wishProductRepository;

    public WishProductService(WishProductRepository wishProductRepository) {
        this.wishProductRepository = wishProductRepository;
    }

    public void addWishlistProduct(Long productId, UserVo sessionUser) {
        wishProductRepository.deleteByProductIdAndUserId(productId, sessionUser.getId());
    }

    public List<WishProduct> getWishProducts(Long userId) {
        List<WishProduct> wishProducts = wishProductRepository.findAllByUserId(userId);
        return wishProducts;
    }

    public void deleteProducts(Long productId, UserVo sessionUserVo) {
        wishProductRepository.deleteByProductIdAndUserId(productId, sessionUserVo.getId());
    }

}

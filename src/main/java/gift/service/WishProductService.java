package gift.service;

import gift.exception.MemberException;
import gift.exception.ProductException;
import gift.exception.WishListException;
import gift.model.Product;
import gift.model.WishProduct;
import gift.repository.ProductRepository;
import gift.repository.WishProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishProductService {

    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;

    public WishProductService(WishProductRepository wishProductRepository,
        ProductRepository productRepository) {
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getMyWishList(Long memberId) {
        return wishProductRepository.findAllByMemberId(memberId);
    }

    public void addMyWish(Long memberId, Long productId) {

        productRepository.findById(productId)
            .orElseThrow(() -> new ProductException("해당 제품이 존재하지 않습니다."));

        try {
            WishProduct wishProduct = new WishProduct(memberId, productId);
            wishProductRepository.save(wishProduct);
        } catch (DataIntegrityViolationException e) {
            throw new WishListException("해당 제품이 이미 위시 리스트에 존재합니다.");
        }
    }

    public void removeMyWish(Long memberId, Long productId) {
        wishProductRepository.findByMemberIdAndProductId(memberId, productId)
            .ifPresentOrElse(wishProductRepository::delete
                , () -> {
                    throw new WishListException("해당 상품이 위시 리스트에 존재하지 않습니다.");
                }
            );
    }


}

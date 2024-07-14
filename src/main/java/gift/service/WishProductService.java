package gift.service;

import gift.dto.ProductBasicInformation;
import gift.dto.WishProductAddRequest;
import gift.dto.WishProductResponse;
import gift.dto.WishProductUpdateRequest;
import gift.exception.NotFoundElementException;
import gift.model.Member;
import gift.model.Product;
import gift.model.WishProduct;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WishProductService {

    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishProductService(WishProductRepository wishProductRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public WishProductResponse addWishProduct(WishProductAddRequest wishProductAddRequest, Long memberId) {
        var product = productRepository.findById(wishProductAddRequest.productId())
                .orElseThrow(() -> new NotFoundElementException(wishProductAddRequest.productId() + "를 가진 상품이 존재하지 않습니다."));
        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundElementException(memberId + "를 가진 멤버가 존재하지 않습니다."));
        if (wishProductRepository.existsByProductAndMember(product, member)) {
            return updateWishProductWithProductAndMember(product, member, wishProductAddRequest.count());
        }
        var wishProduct = saveWishProductWithWishProductRequest(product, member, wishProductAddRequest.count());
        return getWishProductResponseFromWishProduct(wishProduct);
    }

    public void updateWishProduct(Long id, WishProductUpdateRequest wishProductUpdateRequest) {
        var wishProduct = findWishProductById(id);
        if (wishProductUpdateRequest.count() == 0) {
            deleteWishProduct(id);
            return;
        }
        updateWishProductWithCount(wishProduct, wishProductUpdateRequest.count());
    }

    @Transactional(readOnly = true)
    public List<WishProductResponse> getWishProducts(Long memberId, Pageable pageable) {
        return wishProductRepository.findAllByMemberId(memberId, pageable)
                .stream()
                .map(this::getWishProductResponseFromWishProduct)
                .toList();
    }

    public void deleteWishProduct(Long wishProductId) {
        wishProductRepository.deleteById(wishProductId);
    }

    private WishProduct saveWishProductWithWishProductRequest(Product product, Member member, Integer count) {
        var wishProduct = new WishProduct(product, member, count);
        return wishProductRepository.save(wishProduct);
    }

    private WishProductResponse updateWishProductWithProductAndMember(Product product, Member member, Integer count) {
        var wishProduct = wishProductRepository.findByProductAndMember(product, member)
                .orElseThrow(() -> new NotFoundElementException(product.getId() + "를 가진 상품과, " + member.getId() + "를 가진 멤버를 가진 위시 리스트가 존재하지 않습니다."));
        var updatedCount = wishProduct.getCount() + count;
        var updatedWishProduct = updateWishProductWithCount(wishProduct, updatedCount);
        return getWishProductResponseFromWishProduct(updatedWishProduct);
    }

    private WishProduct updateWishProductWithCount(WishProduct wishProduct, Integer count) {
        wishProduct.updateCount(count);
        wishProductRepository.save(wishProduct);
        return wishProduct;
    }

    private WishProductResponse getWishProductResponseFromWishProduct(WishProduct wishProduct) {
        var product = wishProduct.getProduct();
        var productBasicInformation = ProductBasicInformation.of(product.getId(), product.getName(), product.getPrice());
        return WishProductResponse.of(wishProduct.getId(), productBasicInformation, wishProduct.getCount());
    }

    private WishProduct findWishProductById(Long id) {
        return wishProductRepository.findById(id).orElseThrow(
                () -> new NotFoundElementException(id + "를 가진 위시 리스트가 존재하지 않습니다."));
    }
}

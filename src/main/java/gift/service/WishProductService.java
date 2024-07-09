package gift.service;

import gift.dto.WishProductAddRequest;
import gift.dto.WishProductResponse;
import gift.dto.WishProductUpdateRequest;
import gift.exception.NotFoundElementException;
import gift.model.Member;
import gift.model.Product;
import gift.model.WishProduct;
import gift.repository.WishProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WishProductService {

    private final WishProductRepository wishProductRepository;
    private final ProductService productService;
    private final MemberService memberService;

    public WishProductService(WishProductRepository wishProductRepository, ProductService productService, MemberService memberService) {
        this.wishProductRepository = wishProductRepository;
        this.productService = productService;
        this.memberService = memberService;
    }

    public WishProductResponse addWishProduct(WishProductAddRequest wishProductAddRequest, Long memberId) {
        memberService.existsById(memberId);
        var product = productService.findProductWithId(wishProductAddRequest.productId());
        var member = memberService.findMemberWithId(memberId);
        if (wishProductRepository.existsByProductAndMember(product, member)) {
            return updateWishProductWithProductAndMember(product, member, wishProductAddRequest.count());
        }
        var wishProduct = saveWishProductWithWishProductRequest(product, member, wishProductAddRequest.count());
        return getWishProductResponseFromWishProduct(wishProduct);
    }

    public void updateWishProduct(Long id, WishProductUpdateRequest wishProductUpdateRequest) {
        var wishProduct = findWishProductWithId(id);
        if (wishProductUpdateRequest.count() == 0) {
            deleteWishProduct(id);
            return;
        }
        updateWishProductWithCount(wishProduct, wishProductUpdateRequest.count());
    }

    public List<WishProductResponse> getWishProducts(Long memberId) {
        return wishProductRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::getWishProductResponseFromWishProduct)
                .toList();
    }

    public void deleteWishProduct(Long id) {
        var wishProduct = findWishProductWithId(id);
        wishProduct.removeWishProduct();
        wishProductRepository.deleteById(id);
    }

    public WishProduct findWishProductWithId(Long id) {
        var wishProduct = wishProductRepository.findById(id);
        if (wishProduct.isEmpty()) throw new NotFoundElementException("존재하지 않는 리소스에 대한 접근입니다.");
        return wishProduct.get();
    }

    private WishProduct saveWishProductWithWishProductRequest(Product product, Member member, Integer count) {
        var wishProduct = new WishProduct(count);
        wishProduct.addProduct(product);
        wishProduct.addMember(member);
        var savedWishProduct = wishProductRepository.save(wishProduct);
        return savedWishProduct;
    }

    private WishProductResponse updateWishProductWithProductAndMember(Product product, Member member, Integer count) {
        var wishProduct = wishProductRepository.findByProductAndMember(product, member);
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
        var product = productService.getProduct(wishProduct.getProduct().getId());
        return WishProductResponse.of(wishProduct.getId(), product, wishProduct.getCount());
    }
}

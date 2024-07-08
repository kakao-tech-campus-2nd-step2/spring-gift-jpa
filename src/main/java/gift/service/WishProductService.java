package gift.service;

import gift.dto.WishProductAddRequest;
import gift.dto.WishProductResponse;
import gift.dto.WishProductUpdateRequest;
import gift.model.WishProduct;
import gift.repository.WishProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        if (wishProductRepository.existsByProductAndMember(wishProductAddRequest.productId(), memberId)) {
            return updateWishProductWithProductAndMember(wishProductAddRequest, memberId);
        }
        var wishProduct = createWishProductWithWishProductRequest(wishProductAddRequest, memberId);
        var savedWishProduct = wishProductRepository.save(wishProduct);
        return getWishProductResponseFromWishProduct(savedWishProduct);
    }

    public void updateWishProduct(Long id, WishProductUpdateRequest wishProductUpdateRequest) {
        var wishProduct = wishProductRepository.findById(id);
        if (wishProductUpdateRequest.count() == 0) {
            deleteWishProduct(id);
            return;
        }
        updateWishProductWithCount(wishProduct, wishProductUpdateRequest.count());
    }

    private WishProductResponse updateWishProductWithProductAndMember(WishProductAddRequest wishProductAddRequest, Long memberId) {
        var wishProduct = wishProductRepository.findByProductAndMember(wishProductAddRequest.productId(), memberId);
        var count = wishProduct.getCount() + wishProductAddRequest.count();
        var updatedWishProduct = updateWishProductWithCount(wishProduct, count);
        return getWishProductResponseFromWishProduct(updatedWishProduct);
    }

    public List<WishProductResponse> getWishProducts(Long memberId) {
        return wishProductRepository.findAll(memberId)
                .stream()
                .map(this::getWishProductResponseFromWishProduct)
                .toList();
    }

    public void deleteWishProduct(Long id) {
        wishProductRepository.deleteById(id);
    }

    private WishProduct createWishProductWithWishProductRequest(WishProductAddRequest wishProductAddRequest, Long memberId) {
        return new WishProduct(wishProductAddRequest.productId(), memberId, wishProductAddRequest.count());
    }

    private WishProduct updateWishProductWithCount(WishProduct wishProduct, Integer count) {
        wishProduct.updateCount(count);
        wishProductRepository.update(wishProduct);
        return wishProduct;
    }

    private WishProductResponse getWishProductResponseFromWishProduct(WishProduct wishProduct) {
        var product = productService.getProduct(wishProduct.getProductId());
        return WishProductResponse.of(wishProduct.getId(), product, wishProduct.getCount());
    }
}

package gift.service;

import gift.domain.WishProduct;
import gift.domain.WishProduct.Builder;
import gift.repository.MemberJpaRepository;
import gift.repository.ProductJpaRepository;
import gift.repository.WishProductJpaRepository;
import gift.web.dto.request.wishproduct.CreateWishProductRequest;
import gift.web.dto.request.wishproduct.UpdateWishProductRequest;
import gift.web.dto.response.wishproduct.CreateWishProductResponse;
import gift.web.dto.response.wishproduct.ReadAllWishProductsResponse;
import gift.web.dto.response.wishproduct.ReadWishProductResponse;
import gift.web.dto.response.wishproduct.UpdateWishProductResponse;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WishProductService {

    private final ProductJpaRepository productJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final WishProductJpaRepository wishProductJpaRepository;

    public WishProductService(ProductJpaRepository productJpaRepository,
        MemberJpaRepository memberJpaRepository,
        WishProductJpaRepository wishProductJpaRepository) {
        this.productJpaRepository = productJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.wishProductJpaRepository = wishProductJpaRepository;
    }

    /**
     * 위시 리스트에 상품을 추가합니다.
     * 이미 존재하는 WishProduct인 경우 수량만 추가합니다.
     * @param memberId 회원 ID
     * @param request 상품 정보
     * @return 생성된 WishProduct ID
     */
    @Transactional
    public CreateWishProductResponse createWishProduct(Long memberId, CreateWishProductRequest request) {

        // 이미 존재하는 WishProduct인 경우 수량만 추가
        Optional<WishProduct> existingWishProduct = wishProductJpaRepository.findByMemberIdAndProductId(
            memberId, request.getProductId());
        if (existingWishProduct.isPresent()) {
            WishProduct wishProduct = existingWishProduct.get();
            wishProduct.addQuantity(request.getQuantity());

            return CreateWishProductResponse.fromEntity(wishProduct);
        }

        WishProduct wishProduct = new Builder()
            .member(memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found")))
            .product(productJpaRepository.findById(request.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found")))
            .quantity(request.getQuantity()).build();

        return CreateWishProductResponse.fromEntity(wishProductJpaRepository.save(wishProduct));
    }

    public ReadAllWishProductsResponse readAllWishProducts(Long memberId) {
        return new ReadAllWishProductsResponse(
            wishProductJpaRepository.findByMemberId(memberId)
                .stream()
                .map(ReadWishProductResponse::fromEntity)
                .toList()
        );
    }

    @Transactional
    public UpdateWishProductResponse updateWishProduct(Long wishProductId, UpdateWishProductRequest request) {
        WishProduct wishProduct = wishProductJpaRepository.findById(wishProductId)
            .orElseThrow(NoSuchElementException::new);
        wishProduct.updateQuantity(request.getQuantity());

        return UpdateWishProductResponse.fromEntity(wishProduct);
    }

    @Transactional
    public void deleteWishProduct(Long wishProductId) {
        wishProductJpaRepository.deleteById(wishProductId);
    }
}

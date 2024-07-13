package gift.service;

import gift.domain.WishProduct;
import gift.domain.WishProduct.Builder;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishProductRepository;
import gift.web.dto.request.wishproduct.CreateWishProductRequest;
import gift.web.dto.request.wishproduct.UpdateWishProductRequest;
import gift.web.dto.response.wishproduct.CreateWishProductResponse;
import gift.web.dto.response.wishproduct.ReadAllWishProductsResponse;
import gift.web.dto.response.wishproduct.ReadWishProductResponse;
import gift.web.dto.response.wishproduct.UpdateWishProductResponse;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WishProductService {

    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishProductService(WishProductRepository wishProductRepository,
        ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 위시 리스트에 상품을 추가합니다.
     * 이미 존재하는 WishProduct인 경우 수량만 추가합니다.
     * @param memberId 회원 ID
     * @param request 상품 정보
     * @return 생성된 WishProduct ID
     */
    public CreateWishProductResponse createWishProduct(Long memberId, CreateWishProductRequest request) {

        // 이미 존재하는 WishProduct인 경우 수량만 추가
        Optional<WishProduct> existingWishProduct = wishProductRepository.findByMemberIdAndProductId(memberId, request.getProductId());
        if (existingWishProduct.isPresent()) {
            WishProduct wishProduct = existingWishProduct.get();
            wishProduct.addQuantity(request.getQuantity());
            wishProductRepository.updateQuantity(wishProduct);

            return new CreateWishProductResponse(wishProduct.getId());
        }

        WishProduct wishProduct = new Builder()
            .member(memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found")))
            .product(productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found")))
            .quantity(request.getQuantity()).build();

        return new CreateWishProductResponse(wishProductRepository.save(wishProduct));
    }

    public ReadAllWishProductsResponse readAllWishProducts(Long memberId) {
        return new ReadAllWishProductsResponse(
            wishProductRepository
                .findByMemberId(memberId)
                .stream()
                .map(ReadWishProductResponse::fromEntity)
                .toList()
        );
    }

    public UpdateWishProductResponse updateWishProduct(Long wishProductId, UpdateWishProductRequest request) {
        WishProduct wishProduct = wishProductRepository.findById(wishProductId)
            .orElseThrow(() -> new NoSuchElementException(wishProductId + "에 해당하는 위시 상품이 없습니다."));
        wishProduct.updateQuantity(request.getQuantity());
        wishProductRepository.updateQuantity(wishProduct);
        return UpdateWishProductResponse.fromEntity(wishProduct);
    }

    public void deleteWishProduct(Long wishProductId) {
        boolean isSuccessful = wishProductRepository.deleteById(wishProductId);
        if (!isSuccessful) {
            throw new NoSuchElementException(wishProductId + "에 해당하는 위시 상품이 없습니다.");
        }
    }
}

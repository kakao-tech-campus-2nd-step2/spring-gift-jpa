package gift.service;

import gift.common.exception.DuplicateDataException;
import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.WishInsertRequest;
import gift.controller.dto.request.WishPatchRequest;
import gift.controller.dto.response.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void update(Long id, WishPatchRequest request, Long memberId) {
        Wish wish = wishRepository.findByIdFetchJoin(id)
                .orElseThrow(() -> new EntityNotFoundException("Wish with id " + id + " Does not exist"));
        checkWishByProductIdAndMemberId(wish, request.productId(), memberId);
        if (request.productCount() == 0) {
            deleteByProductId(request.productId(), memberId);
            return;
        }
        wish.updateWish(wish.getMember(), request.productCount(), wish.getProduct());
    }

    public void save(WishInsertRequest request, int productCount, Long memberId) {
        checkProductByProductId(request.productId());
        checkDuplicateWish(request.productId(), memberId);
        Member member = memberRepository.getReferenceById(memberId);
        Product product = productRepository.getReferenceById(request.productId());
        wishRepository.save(new Wish(member, productCount, product));
    }

    public Page<WishResponse> findAllWishPagingByMemberId(Long memberId, Pageable pageable) {
        return wishRepository.findAllByMemberIdFetchJoin(memberId, pageable)
                .map(WishResponse::from);
    }

    public void deleteByProductId(Long productId, Long memberId) {
        wishRepository.deleteByProductIdAndMemberId(productId, memberId);
    }

    private void checkWishByProductIdAndMemberId(Wish wish, Long productId, Long memberId) {
        if ( !wish.containsProduct(productId)|| !wish.isOwner(memberId)) {
            throw new EntityNotFoundException("Product with id " + productId + " does not exist in " + memberId +"'s wish");
        }
    }

    private void checkProductByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product with id " + productId + " does not exist");
        }
    }

    private void checkDuplicateWish(Long productId, Long memberId) {
        if (wishRepository.existsByProductIdAndMemberId(productId, memberId)) {
            throw new DuplicateDataException("Product with id " + productId + " already exists in wish", "Duplicate Wish");
        }
    }
}

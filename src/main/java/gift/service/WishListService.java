package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.WishProduct;
import gift.dto.response.WishProductsResponse;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static gift.constant.Message.ADD_SUCCESS_MSG;
import static gift.constant.Message.DELETE_SUCCESS_MSG;

@Service
public class WishListService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishListService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public Page<WishProductsResponse> getWishList(Long memberId, int page) {
        List<WishProductsResponse> wishProducts = getWishProducts(memberId);

        PageRequest pageRequest = PageRequest.of(page, 3);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), wishProducts.size());
        return new PageImpl<>(wishProducts.subList(start, end), pageRequest, wishProducts.size());
    }

    public String addWishProduct(WishProduct wishProduct) {
        wishRepository.save(wish(wishProduct.getMemberId(), wishProduct.getProductId()));
        return ADD_SUCCESS_MSG;
    }

    public String deleteWishProduct(WishProduct wishProduct) {
        wishRepository.delete(wish(wishProduct.getMemberId(), wishProduct.getProductId()));
        return DELETE_SUCCESS_MSG;
    }

    private Wish wish(Long memberId, Long productId) {
        Member member = memberRepository.findMemberById(memberId).get();
        Product product = productRepository.findProductById(productId).get();
        return new Wish(member, product);
    }

    private List<WishProductsResponse> getWishProducts(Long memberId) {
        List<Wish> wishes = wishRepository.findWishByMemberId(memberId);
        return wishes.stream()
                .map(wish -> new WishProductsResponse(wish.getProduct())).toList();
    }
}

package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.WishProduct;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Product> getWishList(Long memberId) {
        Member member = memberRepository.findMemberById(memberId).get();
        return findProducts(wishRepository.findWishByMember(member));
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

    private List<Product> findProducts(List<Wish> wishes) {
        List<Product> products = new ArrayList<>();
        for (Wish wish : wishes) {
            System.out.println(products);
            products.add(productRepository.findProductById(wish.getProduct().getId()).get());
        }
        return products;
    }
}

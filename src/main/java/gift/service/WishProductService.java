package gift.service;

import gift.dto.ProductResponse;
import gift.dto.WishProductAddRequest;
import gift.dto.WishProductResponse;
import gift.dto.WishProductUpdateRequest;
import gift.helper.RepositoryReader;
import gift.model.Member;
import gift.model.Product;
import gift.model.WishProduct;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WishProductService {

    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final RepositoryReader repositoryReader;

    public WishProductService(WishProductRepository wishProductRepository, ProductRepository productRepository, MemberRepository memberRepository, RepositoryReader repositoryReader) {
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.repositoryReader = repositoryReader;
    }

    public WishProductResponse addWishProduct(WishProductAddRequest wishProductAddRequest, Long memberId) {
        var product = repositoryReader.findEntityById(productRepository, wishProductAddRequest.productId());
        var member = repositoryReader.findEntityById(memberRepository, memberId);
        if (wishProductRepository.existsByProductAndMember(product, member)) {
            return updateWishProductWithProductAndMember(product, member, wishProductAddRequest.count());
        }
        var wishProduct = saveWishProductWithWishProductRequest(product, member, wishProductAddRequest.count());
        return getWishProductResponseFromWishProduct(wishProduct);
    }

    public void updateWishProduct(Long id, WishProductUpdateRequest wishProductUpdateRequest) {
        var wishProduct = repositoryReader.findEntityById(wishProductRepository, id);
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
        var wishProduct = repositoryReader.findEntityById(wishProductRepository, id);
        wishProduct.removeWishProduct();
        wishProductRepository.deleteById(id);
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
        var product = wishProduct.getProduct();
        var productResponse = ProductResponse.of(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
        return WishProductResponse.of(wishProduct.getId(), productResponse, wishProduct.getCount());
    }
}

package gift.service;

import gift.domain.Product;
import gift.domain.Wish;
import gift.domain.member.Member;
import gift.exception.ProductAlreadyInWishlistException;
import gift.exception.ProductNotFoundException;
import gift.exception.ProductNotInWishlistException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import gift.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> getProducts(Member member, Pageable pageable) {
        Page<Wish> wishes = wishRepository.findByMember(member, pageable);

        List<ProductResponse> response = wishes.stream()
                .map(Wish::getProduct)
                .map(Product::toDto)
                .toList();

        return new PageImpl<>(response, pageable, response.size());
    }

    public void addProduct(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (wishRepository.existsByMemberIdAndProductId(member.getId(), productId)) {
            throw new ProductAlreadyInWishlistException();
        }

        Wish wish = new Wish(member, product);

        wishRepository.save(wish);
    }

    public void removeProduct(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        Wish wish = wishRepository.findByMemberAndProduct(member, product)
                .orElseThrow(ProductNotInWishlistException::new);

        wishRepository.delete(wish);
    }
    
}

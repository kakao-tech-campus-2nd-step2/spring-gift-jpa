package gift.service;

import gift.dto.WishRequest;
import gift.exception.product.ProductNotFoundException;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
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

    public Wish makeWish(WishRequest request, Member member) {
        productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("해당 productId의 상품을 찾을 수 없습니다."));
        Wish wish = new Wish(request.productId(), member.getId());
        wishRepository.save(wish);
        return wish;
    }

    public List<Product> getAllWishProductsByMember(Member member) {
        return wishRepository.findAllByUserId(member.getId());
    }

    public void deleteWish(Long productId, Member member) {
        if (getAllWishProductsByMember(member).isEmpty()) {
            throw new ProductNotFoundException("해당 productId의 상품이 위시리스트에 존재하지 않습니다.");
        }
        wishRepository.deleteById(productId);
    }
}

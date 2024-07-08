package gift.product.service;

import gift.product.dto.LoginMember;
import gift.product.dto.WishDto;
import gift.product.model.Wish;
import gift.product.repository.WishRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, ProductService productService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
    }

    public List<Wish> getWishAll(LoginMember loginMember) {
        return wishRepository.findAll(loginMember);
    }

    public Wish getWish(Long id, LoginMember loginMember) {
        return getValidatedWish(id, loginMember);
    }

    public Wish insertWish(WishDto wishDto, LoginMember loginMember) {
        productService.getProduct(wishDto.productId());

        Wish wish = new Wish(loginMember.id(), wishDto.productId());
        wish = wishRepository.save(wish);

        return wish;
    }

    public void deleteWish(Long id, LoginMember loginMember) {
        getValidatedWish(id, loginMember);
        wishRepository.delete(id);
    }

    private Wish getValidatedWish(Long id, LoginMember loginMember) {
        try {
            return wishRepository.findById(id, loginMember);
        } catch (DataAccessException e) {
            throw new NoSuchElementException("해당 ID의 상품이 위시리스트에 존재하지 않습니다.");
        }
    }
}

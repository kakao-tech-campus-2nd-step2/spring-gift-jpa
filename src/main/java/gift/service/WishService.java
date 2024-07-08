package gift.service;

import gift.domain.Wish.createWish;
import gift.domain.Wish.wishDetail;
import gift.domain.Wish.wishSimple;
import gift.errorException.BaseHandler;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public wishDetail getWish(long id) {
        if (!wishRepository.validateId(id)) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "위시리스트에 존재하지 않습니다.");
        }
        return wishRepository.getWish(id);
    }

    public List<wishSimple> getWishList(Long userId) {
        return wishRepository.getWishList(userId);
    }

    public int createWish(Long userId, createWish create) {
        if (!productRepository.validateId(create.getProductId())) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다.");
        }
        if (wishRepository.existWish(userId, create)) {
            throw new BaseHandler(HttpStatus.ALREADY_REPORTED, "이미 위시리스트에 존재 합니다.");
        }
        return wishRepository.createWish(userId, create);
    }

    public int deleteWish(long id) {
        if (!wishRepository.validateId(id)) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "위시리스트에 존재하지 않습니다.");
        }
        return wishRepository.deleteWish(id);
    }
}

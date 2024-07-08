package gift.service;

import gift.dto.WishRequest;
import gift.exception.product.ProductNotFoundException;
import gift.model.Product;
import gift.model.User;
import gift.model.Wish;
import gift.repository.ProductDao;
import gift.repository.WishDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishDao wishDao;
    private final ProductDao productDao;

    public WishService(WishDao wishDao, ProductDao productDao) {
        this.wishDao = wishDao;
        this.productDao = productDao;
    }

    public Wish makeWish(WishRequest request, User user) {
        productDao.find(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("해당 productId의 상품을 찾을 수 없습니다."));
        Wish wish = new Wish(request.productId(), user.getId());
        wishDao.insert(wish);
        return wish;
    }

    public List<Product> getAllWishProductsByUser(User user) {
        return wishDao.findAll(user.getId());
    }

    public void deleteWish(Long productId, User user) {
        if (getAllWishProductsByUser(user).isEmpty()) {
            throw new ProductNotFoundException("해당 productId의 상품이 위시리스트에 존재하지 않습니다.");
        }
        wishDao.delete(productId);
    }
}

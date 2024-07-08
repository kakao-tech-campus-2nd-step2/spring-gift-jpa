package gift.wishes.service;

import gift.core.domain.product.Product;
import gift.core.domain.product.ProductRepository;
import gift.core.domain.product.exception.ProductNotFoundException;
import gift.core.domain.wishes.WishesService;
import gift.core.domain.wishes.WishesRepository;
import gift.core.domain.wishes.exception.WishAlreadyExistsException;
import gift.core.domain.wishes.exception.WishNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishesServiceImpl implements WishesService {
    private final ProductRepository productRepository;
    private final WishesRepository wishesRepository;

    @Autowired
    public WishesServiceImpl(
            WishesRepository wishesRepository,
            ProductRepository productRepository
    ) {
        this.wishesRepository = wishesRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addProductToWishes(Long userId, Product product) {
        if (!productRepository.exists(product.id())) {
            throw new ProductNotFoundException();
        }
        if (wishesRepository.exists(userId, product.id())) {
            throw new WishAlreadyExistsException();
        }
        wishesRepository.saveWish(userId, product.id());
    }

    @Override
    public void removeProductFromWishes(Long userId, Product product) {
        if (!wishesRepository.exists(userId, product.id())) {
            throw new WishNotFoundException();
        }
        wishesRepository.removeWish(userId, product.id());
    }

    @Override
    public List<Product> getWishlistOfUser(Long userId) {
        return List.copyOf(wishesRepository.getWishlistOfUser(userId));
    }
}

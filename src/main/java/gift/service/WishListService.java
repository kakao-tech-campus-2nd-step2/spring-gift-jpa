package gift.service;

import gift.model.Product;
import gift.model.User;
import gift.model.WishList;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public WishList getWishListByUser(String email) {
        User user = userRepository.findByEmail(email);
        return wishListRepository.findByUser(user);
    }

    public void addProductToWishList(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        WishList wishList = wishListRepository.findByUser(user);

        if (wishList == null) {
            wishList = new WishList();
            wishList.setUser(user);
            wishList.setProducts(new ArrayList<>());
        }

        List<Product> products = wishList.getProducts();
        if (!products.contains(product)) {
            products.add(product);
            wishList.setProducts(products);
            wishListRepository.save(wishList);
        }
    }

    @Transactional
    public void removeProductFromWishList(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return;
        }

        WishList wishList = wishListRepository.findByUser(user);
        if (wishList == null) {
            System.out.println("Wishlist not found for user: " + email);
            return;
        }

        List<Product> products = wishList.getProducts();
        if (products.contains(product)) {
            products.remove(product);
            System.out.println("Product removed: " + productId);
            wishList.setProducts(products);
            wishListRepository.save(wishList);
        } else {
            System.out.println("Product not found in wishlist: " + productId);
        }
    }
}
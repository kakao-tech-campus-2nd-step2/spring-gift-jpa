package gift.service;

import gift.model.Product;
import gift.model.User;
import gift.model.WishList;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        return wishListRepository.findByUser(user).orElseGet(() -> {
            WishList newWishList = new WishList();
            newWishList.setUser(user);
            newWishList.setProducts(new ArrayList<>());
            return newWishList;
        });
    }

    public void addProductToWishList(String email, Product product) {
        User user = userRepository.findByEmail(email);
        Optional<WishList> optionalWishList = wishListRepository.findByUser(user);

        WishList wishList = optionalWishList.orElseGet(() -> {
            WishList newWishList = new WishList();
            newWishList.setUser(user);
            newWishList.setProducts(new ArrayList<>());
            return newWishList;
        });

        if (product.getId() == null) {
            System.out.println("Product ID is null. Cannot add to wish list.");
            return;
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
        Product product = productRepository.findById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return;
        }

        Optional<WishList> optionalWishList = wishListRepository.findByUser(user);
        if (optionalWishList.isEmpty()) {
            System.out.println("Wishlist not found for user: " + email);
            return;
        }

        WishList wishList = optionalWishList.get();
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
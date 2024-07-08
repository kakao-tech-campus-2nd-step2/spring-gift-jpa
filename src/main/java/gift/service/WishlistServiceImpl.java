package gift.service;

import gift.dto.ProductDTO;
import gift.dto.WishlistDTO;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository, ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
    }

    @Override
    public List<WishlistDTO> getWishlistByUser(String username) {
        List<Wishlist> wishlistEntities = wishlistRepository.findByUsername(username);
        return wishlistEntities.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void addToWishlist(String username, Long productId, int quantity) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUsername(username);
        wishlist.setProductId(productId);
        wishlist.setQuantity(quantity);
        wishlistRepository.save(wishlist);
    }

    @Override
    public void removeFromWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }

    @Override
    public void updateQuantity(Long id, int quantity) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid wishlist Id:" + id));
        wishlist.setQuantity(quantity);
        wishlistRepository.save(wishlist);
    }

    private WishlistDTO convertToDTO(Wishlist wishlist) {
        ProductDTO product = productService.getProductById(wishlist.getProductId());
        return new WishlistDTO(
            wishlist.getId(),
            wishlist.getProductId(),
            wishlist.getUsername(),
            wishlist.getQuantity(),
            product.getName(),   // 제품 이름 추가
            product.getPrice(),  // 제품 가격 추가
            product.getImageUrl() // 제품 이미지 URL 추가
        );
    }
}


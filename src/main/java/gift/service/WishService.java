package gift.service;

import gift.dto.WishRequestDTO;
import gift.dto.WishResponseDTO;
import gift.model.Product;
import gift.model.User;
import gift.model.Wish;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, UserRepository userRepository, ProductRepository productRepository){

        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;

    }

    public void addWishProduct(Long userId, WishRequestDTO wishRequestDTO) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(wishRequestDTO.productId()).orElse(null);

        Wish wish = new Wish(user, product);
        wishRepository.save(wish);
    }


    public WishResponseDTO getWishlist(Long userId) {
        List<Long> productsId = wishRepository.findProductIdsByUserId(userId);
        return new WishResponseDTO(userId, productRepository.findAllById(productsId));
    }

    @Transactional
    public void deleteWishProduct(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        wishRepository.deleteByUserAndProduct(user, product);
    }
}

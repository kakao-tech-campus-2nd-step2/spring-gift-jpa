package gift.service;

import gift.dto.WishRequestDTO;
import gift.dto.WishResponseDTO;
import gift.model.Wish;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository){

        this.wishRepository = wishRepository;
        this.productRepository = productRepository;

    }

    public void addWishProduct(WishRequestDTO wishRequestDTO) {
        Wish wish = new Wish(wishRequestDTO.userId(), wishRequestDTO.productId());
        wishRepository.save(wish);
    }


    public WishResponseDTO getWishlist(Long userId) {
        List<Long> productsId = wishRepository.findProductIdsByUserId(userId);
        return new WishResponseDTO(userId, productRepository.findAllById(productsId));
    }

    public void deleteWishProduct(Long userId, Long productId) {
        wishRepository.deleteByUserIdAndProductId(userId, productId);
    }
}

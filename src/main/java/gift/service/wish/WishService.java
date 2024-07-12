package gift.service.wish;

import gift.domain.wish.Wish;
import gift.domain.wish.WishRepository;
import gift.web.dto.WishDto;
import gift.web.exception.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<WishDto> getWishes(String email) {
        return wishRepository.findAll()
            .stream()
            .map(WishDto::from)
            .toList();
    }

    public WishDto createWish(String email, WishDto wishDto) {
        return WishDto.from(wishRepository.save(WishDto.toEntity(wishDto, email)));
    }

    public WishDto updateWish(String email, WishDto wishDto) {
        wishRepository.findByEmailAndProductId(email, wishDto.productId())
                .orElseThrow(() -> new ProductNotFoundException("위시 제품이 없슴다."));
        Wish newWish = WishDto.toEntity(wishDto, email);
        wishRepository.save(newWish);
        return WishDto.from(newWish);
    }

    public void deleteWish(String email, Long productId) {
        wishRepository.findByEmailAndProductId(email, productId)
            .orElseThrow(() -> new ProductNotFoundException("위시 제품이 없슴다."));
        wishRepository.deleteByEmailAndProductId(email, productId);
    }
}

package gift.service;

import gift.dto.WishDto;
import gift.entity.Wish;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class WishService {
    private WishRepository wishRepository;
    private ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public Wish save(WishDto wishDto) {

        Long userId = translateIdFrom(wishDto.getToken());
        Long productId = wishDto.getProductId();

        return wishRepository.save(productId, userId);
    }

    public List<WishDto> getAll(String token) {

        Long userId = translateIdFrom(token);
        List<Wish> wishes = wishRepository.getAll(userId);


        List<WishDto> wishDtos = wishes.stream().map(WishDto::fromEntity).toList();
        return wishDtos;
    }

    public void delete(Long id, String token) throws IllegalAccessException {
        Long userId = translateIdFrom(token);
        wishRepository.delete(id, userId);
    }

    private Long translateIdFrom(String token) {

        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedToken = new String(decodedBytes);
        String[] userInfo = decodedToken.split(":");
        String userId = userInfo[0];

        return Long.parseLong(userId);
    }
}
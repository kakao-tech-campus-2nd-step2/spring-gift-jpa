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

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public Wish save(WishDto request) {

        Long id = translateIdFrom(request.getToken());
        Long productId = request.getProductId();

        return wishRepository.save(productId, id);

    }

    public List<WishDto> getAll(String userId) {

        List<Wish> wishes = wishRepository.getAll(userId);

        List<WishDto> wishDtos = wishes.stream().map(WishDto::fromEntity).toList();
        return wishDtos;
    }

    //delete 보통 크게응답안하면 void로 하기도함
    public void delete(Long id, String token) {
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
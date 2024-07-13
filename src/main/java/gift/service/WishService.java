package gift.service;

import gift.dto.TokenDto;
import gift.dto.WishDto;
import gift.entity.Wish;
import gift.repository.ProductRepositoryInterface;
import gift.repository.WishRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepositoryInterface wishRepositoryInterface;
    private final ProductRepositoryInterface productRepositoryInterface;
    private final TokenService tokenService;

    public WishService(WishRepositoryInterface wishRepositoryInterface,
                       ProductRepositoryInterface productRepositoryInterface,
                       TokenService tokenService) {

        this.wishRepositoryInterface = wishRepositoryInterface;
        this.productRepositoryInterface = productRepositoryInterface;
        this.tokenService = tokenService;

    }

    public WishDto save(Long productId, String tokenValue) {

        Long userId = translateIdFrom(tokenValue);
        Wish newWish = new Wish(userId, productId);

        return WishDto.fromEntity(wishRepositoryInterface.save(newWish));
    }

    public List<WishDto> getAll(TokenDto tokenDto) {

        Long userId = translateIdFrom(tokenDto);
        List<Wish> wishes = wishRepositoryInterface.findAllByUserId(userId);

        List<WishDto> wishDtos = wishes.stream().map(WishDto::fromEntity).toList();
        return wishDtos;
    }

    public void delete(Long id, TokenDto tokenDto) throws IllegalAccessException {

        Long userId = translateIdFrom(tokenDto);
        Wish candidateWish = wishRepositoryInterface.findById(id).get();
        Long wishUserId = candidateWish.getUserId();

        if (userId.equals(wishUserId)) {
            wishRepositoryInterface.delete(candidateWish);
        }
    }

    private Long translateIdFrom(TokenDto tokenDto) {

        String tokenValue = tokenDto.getTokenValue();
        String decodedToken = tokenService.decodeTokenValue(tokenValue);
        String[] userInfo = decodedToken.split(" ");
        String userId = userInfo[1];

        return Long.parseLong(userId);
    }

    private Long translateIdFrom(String tokenValue) {

        String decodedToken = tokenService.decodeTokenValue(tokenValue);
        String[] userInfo = decodedToken.split(" ");
        String userId = userInfo[1];

        return Long.parseLong(userId);
    }
}
package gift.service;

import gift.dto.TokenDto;
import gift.dto.WishDto;
import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import gift.repository.ProductRepositoryInterface;
import gift.repository.UserRepositoryInterface;
import gift.repository.WishRepositoryInterface;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class WishService {
    private final WishRepositoryInterface wishRepositoryInterface;
    private final ProductRepositoryInterface productRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final TokenService tokenService;

    public WishService(WishRepositoryInterface wishRepositoryInterface,
                       ProductRepositoryInterface productRepositoryInterface,
                       UserRepositoryInterface userRepositoryInterface,
                       TokenService tokenService) {

        this.wishRepositoryInterface = wishRepositoryInterface;
        this.productRepositoryInterface = productRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.tokenService = tokenService;

    }

    public WishDto.Response save(Long productId, String tokenValue) {

        Long userId = translateIdFrom(tokenValue);
        User user = userRepositoryInterface.findById(userId).get();
        Product product =productRepositoryInterface.findById(productId).get();
        Wish newWish = new Wish(product,user);

        return WishDto.Response.fromEntity(wishRepositoryInterface.save(newWish));
    }

    public List<WishDto.Response> getAll(TokenDto tokenDto) {

        Long userId = translateIdFrom(tokenDto);
        List<Wish> wishes = wishRepositoryInterface.findAllByUserId(userId);

        List<WishDto.Response> wishDtos = wishes.stream().map(WishDto.Response::fromEntity).toList();
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

    public Page<Wish> getWishes(Pageable pageable) {
        return wishRepositoryInterface.findAll(pageable);
    }
}
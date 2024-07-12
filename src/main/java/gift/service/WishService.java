package gift.service;

import gift.domain.Product;
import gift.domain.User;
import gift.domain.Wish;
import gift.dto.requestDTO.WishRequestDTO;
import gift.dto.responseDTO.WishListResponseDTO;
import gift.dto.responseDTO.WishResponseDTO;
import gift.repository.JpaProductRepository;
import gift.repository.JpaUserRepository;
import gift.repository.JpaWishRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishService {
    private final JpaWishRepository jpaWishRepository;
    private final JpaProductRepository jpaProductRepository;
    private final JpaUserRepository jpaUserRepository;

    public WishService(JpaWishRepository jpaWishRepository,
        JpaProductRepository jpaProductRepository,
        JpaUserRepository jpaUserRepository) {
        this.jpaWishRepository = jpaWishRepository;
        this.jpaProductRepository = jpaProductRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Transactional(readOnly = true)
    public WishListResponseDTO getAllWishes(Long userId) {
        List<WishResponseDTO> wishResponseDTOList = jpaWishRepository.findAllByUserId(userId)
            .stream()
            .map(WishResponseDTO::of)
            .toList();
        return new WishListResponseDTO(wishResponseDTOList);
    }

    @Transactional(readOnly = true)
    public WishResponseDTO getOneWish(Long wishId) {
        Wish wish = jpaWishRepository.findById(wishId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        return WishResponseDTO.of(wish);
    }

    public Long addWish(WishRequestDTO wishRequestDTO) {
        //TODO: db에 존재하는 product는 insert하면 안됨
        User user = jpaUserRepository.findById(wishRequestDTO.userId())
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        Product product = jpaProductRepository.findById(wishRequestDTO.productId())
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        Wish wish = new Wish(user, product, wishRequestDTO.count());
        return jpaWishRepository.save(wish).getId();
    }

    public Long deleteWish(Long wishId) {
        Wish wish = jpaWishRepository.findById(wishId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        jpaWishRepository.delete(wish);
        return wish.getId();
    }
}

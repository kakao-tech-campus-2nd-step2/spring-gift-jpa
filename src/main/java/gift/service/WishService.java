package gift.service;

import gift.domain.Wish;
import gift.dto.requestDTO.WishRequestDTO;
import gift.dto.responseDTO.WishListResponseDTO;
import gift.dto.responseDTO.WishResponseDTO;
import gift.repository.JpaWishRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishService {
    private final JpaWishRepository jpaWishRepository;

    public WishService(JpaWishRepository jpaWishRepository) {
        this.jpaWishRepository = jpaWishRepository;
    }

    @Transactional(readOnly = true)
    public WishListResponseDTO getAllWishes(Long userId){
        List<WishResponseDTO> wishResponseDTOList = jpaWishRepository.findAllByUserId(userId)
            .stream()
            .map(WishResponseDTO::of)
            .toList();
        return new WishListResponseDTO(wishResponseDTOList);
    }

    @Transactional(readOnly = true)
    public WishResponseDTO getOneWish(Long wishId){
        Wish wish = jpaWishRepository.findById(wishId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        return WishResponseDTO.of(wish);
    }

    public Long addWish(WishRequestDTO wishRequestDTO){
        //TODO: db에 존재하는 product는 insert하면 안됨
        Wish wish = new Wish(wishRequestDTO.userId(),
            wishRequestDTO.productId(), wishRequestDTO.count());
        return jpaWishRepository.save(wish).getId();
    }

    public Long deleteWish(Long wishId){
        Wish wish = jpaWishRepository.findById(wishId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        jpaWishRepository.delete(wish);
        return wish.getId();
    }
}

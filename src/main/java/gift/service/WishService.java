package gift.service;

import gift.domain.Wish;
import gift.dto.requestDTO.WishRequestDTO;
import gift.dto.responseDTO.WishListResponseDTO;
import gift.dto.responseDTO.WishResponseDTO;
import gift.repository.WishRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public WishListResponseDTO getAllWishes(Long userId){
        List<WishResponseDTO> wishResponseDTOList = wishRepository.selectAllWish(userId)
            .stream()
            .map(WishResponseDTO::of)
            .toList();
        return new WishListResponseDTO(wishResponseDTOList);
    }

    public WishResponseDTO getOneWish(Long wishId){
        Wish wish = wishRepository.selectWish(wishId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        return WishResponseDTO.of(wish);
    }

    public Long addWish(WishRequestDTO wishRequestDTO){
        //TODO: db에 존재하는 product는 insert하면 안됨
        Wish wish = new Wish(wishRequestDTO.userId(),
            wishRequestDTO.productId(), wishRequestDTO.count());
        return wishRepository.insertWish(wish);
    }

    public Long deleteWish(Long wishId){
        Wish wish = wishRepository.selectWish(wishId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        wishRepository.deleteWish(wishId);
        return wish.getId();
    }
}

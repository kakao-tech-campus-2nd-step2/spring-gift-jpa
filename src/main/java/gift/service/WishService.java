package gift.service;

import gift.dto.response.WishResponseDto;
import gift.exception.EntityNotFoundException;
import gift.exception.ForbiddenException;
import gift.repository.product.ProductRepository;
import gift.repository.wish.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public List<WishResponseDto> findAllWish(String email){
        return wishRepository.findWishByMemberEmail(email).stream()
                .map(WishResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long addWish(Long productId, String email, int count){
        productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("이미 WISH LIST에 등록한 상품입니다."));

        return wishRepository.wishSave(productId, email, count);
    }

    @Transactional
    public Long editWish(Long wishId, String email, int count){
        wishRepository.findById(wishId).orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 상품입니다."));
        Integer findCount = wishRepository.findWishCountByWishIdAndMemberEmail(wishId, email);

        if(findCount == 0){
            throw new ForbiddenException();
        }


        return wishRepository.updateWish(wishId, count);
    }

    @Transactional
    public Long deleteWish(Long wishId, String email){
        wishRepository.findById(wishId).orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 상품입니다"));
        Integer findCount = wishRepository.findWishCountByWishIdAndMemberEmail(wishId, email);

        if(findCount == 0){
            throw new ForbiddenException();
        }

        wishRepository.deleteWish(wishId);

        return wishId;
    }
}

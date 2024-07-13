package gift.Service;

import gift.DTO.WishDTO;
import gift.Entity.WishEntity;
import gift.Repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishService {

    @Autowired
    private WishRepository wishRepository;

    public List<WishEntity> findAllWishes() {
        return wishRepository.findAll();
    }

    public Optional<WishEntity> findWishById(Long id) {
        return wishRepository.findById(id);
    }

    public WishEntity saveWish(WishEntity wishEntity) {
        return wishRepository.save(wishEntity);
    }

    public void deleteWish(Long id) {
        wishRepository.deleteById(id);
    }

    public Page<WishDTO> getWishes(Pageable pageable) {
        Page<WishEntity> wishPage = wishRepository.findAll(pageable);
        return wishPage.map(this::convertToDTO);
    }

    private WishDTO convertToDTO(WishEntity wishEntity) {
        return new WishDTO(
                wishEntity.getId(),
                wishEntity.getUser().getId(),
                wishEntity.getProduct().getId(),
                wishEntity.getProductName()
        );
    }
}

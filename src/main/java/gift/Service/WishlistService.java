package gift.Service;

import gift.Entity.Wishlist;
import gift.Mapper.Mapper;
import gift.Model.WishlistDto;
import gift.Repository.WishlistJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistJpaRepository wishListJpaRepository;
    private final Mapper mapper;

    @Autowired
    public WishlistService(WishlistJpaRepository wishListJpaRepository, Mapper mapper) {
        this.wishListJpaRepository = wishListJpaRepository;
        this.mapper = mapper;
    }


    public Page<WishlistDto> getWishlistByPage(long userId, Pageable pageable) {
        Page<Wishlist> wishlistPage = wishListJpaRepository.findByUserId(userId, pageable);
        return wishlistPage.map(mapper::wishlistToDto);
    }


    public List<WishlistDto> getWishlist(long userId) {
        List<Wishlist> wishlist = wishListJpaRepository.findByIdUserId(userId);
        List<WishlistDto> wishlistToDto = wishlist.stream()
                .map(mapper::wishlistToDto)
                .collect(Collectors.toList());
        return wishlistToDto;
    }

    public void addWishlistItem(WishlistDto wishlistDto) {
        wishlistDto.setPrice(wishlistDto.getPrice() * wishlistDto.getCount());
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        wishListJpaRepository.save(wishlist);
    }

    public void removeWishlistItem(WishlistDto wishListDto, WishlistDto wishlistOptional) {
        int newCount = wishListDto.getCount() - wishListDto.getQuantity();

        if (newCount > 0) {
            wishlistOptional.setCount(newCount);
            wishlistOptional.setPrice(wishlistOptional.getPrice() / wishListDto.getCount() * newCount);

            wishListJpaRepository.save(mapper.wishlistDtoToEntity(wishlistOptional));
            return;
        }

        wishListJpaRepository.delete(mapper.wishlistDtoToEntity(wishlistOptional));
    }

}

package gift.service;

import gift.exception.ErrorCode;
import gift.exception.RepositoryException;
import gift.model.Member;
import gift.model.Product;
import gift.model.WishList;
import gift.model.WishListDTO;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishListService(WishListRepository wishListRepository,
        ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public WishListDTO createWishList(WishListDTO wishListDTO) {
        Product product = productRepository.findByName(wishListDTO.productName())
            .orElseThrow(() -> new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND,
                wishListDTO.productName()));
        Member member = memberRepository.findById(wishListDTO.memberId())
            .orElseThrow(() -> new RepositoryException(ErrorCode.MEMBER_NOT_FOUND,
                wishListDTO.memberId()));
        WishList wishList = new WishList(wishListDTO.email(), member,
            product, wishListDTO.quantity());
        return convertToDTO(wishListRepository.save(wishList));
    }

    public List<WishListDTO> getAllWishList() {
        List<WishList> wishlists = wishListRepository.findAll();
        return wishlists.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<WishListDTO> getWishListByMemberId(long memberId) {
        List<WishList> wishlists = wishListRepository.findWishListByMemberId(memberId);
        return wishlists.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public WishListDTO updateWishListQuantity(WishListDTO wishListDTO) {
        WishList currentWishList = wishListRepository.findByMemberIdAndProductName(
                wishListDTO.memberId(),
                wishListDTO.productName())
            .orElseThrow(() -> new RepositoryException(
                ErrorCode.WISHLIST_NOT_FOUND, wishListDTO.memberId(), wishListDTO.productName()));

        Product product = productRepository.findByName(wishListDTO.productName()).
            orElseThrow(() -> new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND,
                wishListDTO.productName()));
        Member member = memberRepository.findById(wishListDTO.memberId())
            .orElseThrow(() -> new RepositoryException(ErrorCode.MEMBER_NOT_FOUND,
                wishListDTO.memberId()));
        WishList newWishList = new WishList(currentWishList.getId(), currentWishList.getEmail(),
            member, product, wishListDTO.quantity());
        return convertToDTO(wishListRepository.save(newWishList));
    }

    public void deleteWishList(long memberId, String productName) {
        WishList wishList = wishListRepository.findByMemberIdAndProductName(memberId, productName)
            .orElseThrow(() -> new RepositoryException(ErrorCode.WISHLIST_NOT_FOUND,
                memberId, productName));
        wishListRepository.deleteById(wishList.getId());
    }

    private WishListDTO convertToDTO(WishList wishList) {
        long memberId = wishList.getMember().getId();
        String productName = wishList.getProduct().getName();
        return new WishListDTO(wishList.getEmail(), memberId, productName,
            wishList.getQuantity());
    }
}

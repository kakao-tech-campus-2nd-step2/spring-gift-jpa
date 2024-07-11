package gift.service;

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

    public void createWishList(WishListDTO wishListDTO) {
        Product product = productRepository.findByName(wishListDTO.productName())
            .orElseThrow(() -> new RepositoryException("해당 상품을 찾을 수 없습니다."));
        Member member = memberRepository.findById(wishListDTO.memberId())
            .orElseThrow(() -> new RepositoryException("해당 사용자를 찾을 수 업습니다."));
        WishList wishList = new WishList(wishListDTO.email(), member,
            product, wishListDTO.quantity());
        wishListRepository.save(wishList);
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

    public void updateWishListQuantity(WishListDTO wishListDTO) {
        WishList currentWishList = wishListRepository.findByMemberIdAndProductName(
            wishListDTO.memberId(),
            wishListDTO.productName()).orElseThrow(() -> new RepositoryException(
            wishListDTO.email() + "의 사용자의 위시 리스트에서 " + wishListDTO.productName()
                + "을(를) 찾지 못했습니다."));

        Product product = productRepository.findByName(wishListDTO.productName()).
            orElseThrow(() -> new RepositoryException("해당 상품을 찾을 수 없습니다."));
        Member member = memberRepository.findById(wishListDTO.memberId())
            .orElseThrow(() -> new RepositoryException("해당 사용자를 찾을 수 없습니다."));
        WishList newWishList = new WishList(currentWishList.getId(), currentWishList.getEmail(),
            member, product, wishListDTO.quantity());
        wishListRepository.save(newWishList);
    }

    public void deleteWishList(long memberId, String productName) {
        WishList wishList = wishListRepository.findByMemberIdAndProductName(memberId, productName)
            .orElseThrow(() -> new RepositoryException(
                "해당 사용자의 위시 리스트에서 " + productName + "을(를) 찾지 못해 지울 수 없습니다."));
        wishListRepository.deleteById(wishList.getId());
    }

    private WishListDTO convertToDTO(WishList wishList) {
        long memberId = wishList.getMember().getId();
        String productName = wishList.getProduct().getName();
        return new WishListDTO(wishList.getEmail(), memberId, productName,
            wishList.getQuantity());
    }
}

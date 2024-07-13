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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Product product = productRepository.findById(wishListDTO.productId())
            .orElseThrow(() -> new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND,
                wishListDTO.productId()));
        Member member = memberRepository.findById(wishListDTO.memberId())
            .orElseThrow(() -> new RepositoryException(ErrorCode.MEMBER_ID_NOT_FOUND,
                wishListDTO.memberId()));
        WishList wishList = new WishList(member,
            product, wishListDTO.quantity());
        return convertToDTO(wishListRepository.save(wishList));
    }

    public List<WishListDTO> getAllWishList() {
        List<WishList> wishlists = wishListRepository.findAll();
        return wishlists.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Page<WishListDTO> getWishListByMemberId(long memberId, int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);

        List<WishList> allWishlists = wishListRepository.findWishListByMemberId(memberId);
        List<WishListDTO> allWishListDTO = allWishlists.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allWishlists.size());

        List<WishListDTO> pageContent = allWishListDTO.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allWishListDTO.size());
    }

    public WishListDTO updateWishListQuantity(WishListDTO wishListDTO) {
        WishList currentWishList = wishListRepository.findByMemberIdAndProductId(
                wishListDTO.memberId(),
                wishListDTO.productId())
            .orElseThrow(() -> new RepositoryException(
                ErrorCode.WISHLIST_NOT_FOUND, wishListDTO.memberId(), wishListDTO.productId()));

        Product product = productRepository.findById(wishListDTO.productId()).
            orElseThrow(() -> new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND,
                wishListDTO.productId()));
        Member member = memberRepository.findById(wishListDTO.memberId())
            .orElseThrow(() -> new RepositoryException(ErrorCode.MEMBER_ID_NOT_FOUND,
                wishListDTO.memberId()));
        WishList newWishList = new WishList(currentWishList.getId(), member, product,
            wishListDTO.quantity());
        return convertToDTO(wishListRepository.save(newWishList));
    }

    public void deleteWishList(long memberId, long productId) {
        WishList wishList = wishListRepository.findByMemberIdAndProductId(memberId, productId)
            .orElseThrow(
                () -> new RepositoryException(ErrorCode.WISHLIST_NOT_FOUND, memberId, productId));
        wishListRepository.deleteById(wishList.getId());
    }

    private WishListDTO convertToDTO(WishList wishList) {
        long memberId = wishList.getMember().getId();
        long productId = wishList.getProduct().getId();
        return new WishListDTO(memberId, productId, wishList.getQuantity());
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }
}

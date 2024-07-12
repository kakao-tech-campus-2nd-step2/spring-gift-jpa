package gift.service;

import gift.constants.Messages;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.*;
import gift.exception.WishNotFoundException;
import gift.repository.WishRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, ProductService productService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
    }

    @Transactional
    public void save(MemberRequestDto memberRequestDto, WishRequestDto wishRequestDto){
        ProductResponseDto productResponseDto = productService.findByName(wishRequestDto.getProductName());

        Product product = productResponseDto.toEntity();
        Member member = memberRequestDto.toEntity();

        Wish newWish = new Wish(member,product,wishRequestDto.getQuantity());

        product.addWish(newWish);
        member.addWish(newWish);
        wishRepository.save(newWish);
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getMemberWishesByMemberId(Long memberId){
        return wishRepository.findByMemberId(memberId)
                .stream()
                .map(WishResponseDto::from)
                .toList();

    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getPagedMemberWishesByMemberId(Long memberId, Pageable pageable){
        return wishRepository.findByMemberId(memberId,pageable)
                .stream()
                .map(WishResponseDto::from)
                .toList();
    }

    @Transactional
    public void deleteWishByMemberIdAndId(Long memberId, Long id){
        Wish wish = wishRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));

        wish.getMember().removeWish(wish);
        wish.getProduct().removeWish(wish);
        wishRepository.deleteById(id);
    }

    @Transactional
    public void updateQuantityByMemberIdAndId(Long memberId, Long id, WishRequestDto request){
        Wish existingWish = wishRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));

        Wish updatedWish = new Wish(existingWish.getId(),existingWish.getMember(),existingWish.getProduct(),request.getQuantity());
        wishRepository.save(updatedWish);
    }
}

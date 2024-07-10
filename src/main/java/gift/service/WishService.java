package gift.service;

import gift.constants.Messages;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.*;
import gift.exception.WishNotFoundException;
import gift.repository.WishRepository;
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
        ProductResponseDto productDto = productService.findByName(wishRequestDto.getProductName());
        Product product = new Product(productDto.getId(), productDto.getName(), productDto.getPrice(), productDto.getImageUrl());

        wishRepository.save(new Wish(memberRequestDto.toEntity(),product,wishRequestDto.getQuantity()));
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getMemberWishListByMemberId(Long memberId){
        return wishRepository.findByMemberId(memberId)
                .orElseThrow(() -> new WishNotFoundException(Messages.NOT_FOUND_WISH))
                .stream()
                .map(WishResponseDto::from)
                .toList();

    }

    @Transactional
    public void deleteWishByMemberIdAndWishId(Long memberId, Long id){
        wishRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));
        wishRepository.deleteById(id);
    }

    @Transactional
    public void updateQuantityByMemberIdAndWishId(Long memberId, Long id, WishRequestDto request){
        Wish existingWish = wishRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));

        Wish updatedWish = new Wish(existingWish.getId(),existingWish.getMember(),existingWish.getProduct(),request.getQuantity());
        wishRepository.save(updatedWish);
    }

}

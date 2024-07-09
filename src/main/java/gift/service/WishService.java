package gift.service;

import gift.constants.Messages;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.MemberResponseDto;
import gift.dto.ProductResponseDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.exception.WishNotFoundException;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, ProductService productService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
    }

    public void save(MemberResponseDto memberResponseDto, WishRequestDto request){
        Member member = new Member(memberResponseDto.getId(),memberResponseDto.getEmail(),memberResponseDto.getPassword());

        ProductResponseDto productDto = productService.findByName(request.getProductName());
        Product product = new Product(productDto.getId(), productDto.getName(), productDto.getPrice(), productDto.getImageUrl());

        wishRepository.save(new Wish(member,product,request.getQuantity()));
    }

    public List<WishResponseDto> findByMemberEmail(MemberResponseDto memberResponseDto){
        return wishRepository.findByMemberId(memberResponseDto.getId())
                .orElseThrow(() -> new WishNotFoundException(Messages.NOT_FOUND_WISH))
                .stream()
                .map(WishResponseDto::from)
                .toList();

    }

    public void deleteById(MemberResponseDto memberResponseDto, Long id){
        wishRepository.findByIdAndMemberId(id, memberResponseDto.getId())
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));
        wishRepository.deleteById(id);
    }

    public void updateQuantity(MemberResponseDto memberResponseDto, Long id, WishRequestDto request){
        Wish existingWish = wishRepository.findByIdAndMemberId(id, memberResponseDto.getId())
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));

        Wish updatedWish = new Wish(existingWish.getId(),existingWish.getMember(),existingWish.getProduct(),request.getQuantity());
        wishRepository.save(updatedWish);
    }

}

package gift.service;

import gift.constants.Messages;
import gift.domain.Wish;
import gift.dto.MemberResponseDto;
import gift.dto.ProductResponseDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.exception.WishNotFoundException;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, ProductService productService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
    }

    public void save(MemberResponseDto memberResponseDto, WishRequestDto request){
        ProductResponseDto productDto = productService.findByName(request.getProductName());
        wishRepository.save(new Wish(memberResponseDto.getId(),productDto.getId(),request.getQuantity()));
    }

    public List<WishResponseDto> findByMemberEmail(MemberResponseDto memberResponseDto){
        List<Wish> wishes = wishRepository.findByMemberId(memberResponseDto.getId())
                .orElseThrow(() -> new WishNotFoundException(Messages.NOT_FOUND_WISH));

        List<Long> productIds = wishes.stream()
                .map(Wish::getProductId)
                .toList();

        Map<Long, ProductResponseDto> productMap = productService.findByIds(productIds)
                .stream()
                .collect(Collectors.toMap(ProductResponseDto::getId, Function.identity()));

        return wishes.stream()
                .map(wish -> convertToWishResponseDto(wish,productMap.get(wish.getProductId())))
                .collect(Collectors.toList());
    }

    public void delete(MemberResponseDto memberResponseDto, Long id){
        wishRepository.findByIdAndMemberId(id, memberResponseDto.getId())
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));
        wishRepository.delete(id);
    }

    public void updateQuantity(MemberResponseDto memberResponseDto, Long id, WishRequestDto request){
        wishRepository.findByIdAndMemberId(id, memberResponseDto.getId())
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));
        wishRepository.updateQuantity(id, request.getQuantity());
    }

    private WishResponseDto convertToWishResponseDto(Wish wish, ProductResponseDto productResponseDto) {
        return new WishResponseDto(
                wish.getId(),
                wish.getProductId(),
                productResponseDto.getName(),
                productResponseDto.getPrice(),
                productResponseDto.getImageUrl(),
                wish.getQuantity()
        );
    }
}

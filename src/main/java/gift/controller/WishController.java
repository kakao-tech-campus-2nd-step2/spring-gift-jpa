package gift.controller;

import gift.domain.Wish;
import gift.dto.WishDTO;
import gift.service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }
    @PostMapping
    @ResponseBody
    public void addWish(@RequestBody WishDTO wishDTO) {
        wishService.addWish(wishDTO.getMemberId(), wishDTO.getProductName());
    }

    @GetMapping
    public ResponseEntity<List<WishDTO>> getWishes(@RequestParam Long memberId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "id,asc") String[] sort) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        Page<Wish> wishPage = wishService.getWishes(memberId, pageable);

        List<WishDTO> wishDTOList = wishPage.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(wishDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    @ResponseBody
    public ResponseEntity<Void> removeWish(@RequestBody WishDTO wishDTO) {
        wishService.removeWish(wishDTO.getMemberId(), wishDTO.getProductName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private WishDTO convertToDto(Wish wish) {
        WishDTO dto = new WishDTO();
        dto.setId(wish.getId());
        dto.setMemberId(wish.getMember().getId());
        dto.setProductId(wish.getProduct().getId());
        dto.setProductName(wish.getProduct().getName());
        dto.setProductPrice(wish.getProduct().getPrice());
        dto.setProductImageUrl(wish.getProduct().getImageUrl());
        return dto;
    }
}

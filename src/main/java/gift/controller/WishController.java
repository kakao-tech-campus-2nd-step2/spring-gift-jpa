package gift.controller;

import gift.anotation.LoginMember;
import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishDTO;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    @ResponseBody
    public void addWish(@RequestBody WishDTO wishDTO) {
        wishService.addWish(wishDTO.getMemberId(), wishDTO.getProductName());
    }

    @GetMapping
    public List<WishDTO> getWishes(@RequestParam Long memberId) {
        return wishService.getWishes(memberId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/remove")
    @ResponseBody
    public void removeWish(@RequestBody WishDTO wishDTO) {
        wishService.removeWish(wishDTO.getMemberId(), wishDTO.getProductName());
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

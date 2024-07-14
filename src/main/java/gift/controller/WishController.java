package gift.controller;

import gift.dto.TokenDto;
import gift.dto.WishDto;
import gift.entity.Wish;
import gift.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequestMapping("/wish")
@Controller
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping()
    public void save(@RequestBody WishDto.Request request) {
        wishService.save(request.getProductId(), request.getTokenValue());
    }

    @GetMapping()
    public List<WishDto.Response> getAll(@RequestParam TokenDto token) {
        return wishService.getAll(token);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, @RequestParam TokenDto tokenDto) throws IllegalAccessException {
        wishService.delete(id, tokenDto);
    }

    @GetMapping("/wishes")
    public Page<Wish> getWishes(Pageable pageable) {
        return wishService.getWishes(pageable);
    }

}

package gift.controller;

import gift.dto.WishDto;
import gift.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        wishService.save(new WishDto(
                request.getProductId(),
                request.getToken())
        );
    }

    @GetMapping()
    public List<WishDto> getAll(@RequestParam String token) {
        return wishService.getAll(token);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, @RequestParam String token) throws IllegalAccessException {
        wishService.delete(id, token);
    }
}

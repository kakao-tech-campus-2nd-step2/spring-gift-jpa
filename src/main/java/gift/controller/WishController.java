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

    //insert
    @PostMapping()
    public void save(@RequestBody WishDto.Request request) {
        wishService.save(new WishDto(
                request.getProductId(),
                request.getToken())
        );
    }

    //getAll
    @GetMapping()
    public List<WishDto> getAll(@RequestParam String token) {
        return wishService.getAll(token);
    }

    //delete one by id
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, @RequestParam String token) {
        wishService.delete(id, token);
    }
}

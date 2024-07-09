package gift.controller;

import gift.dto.WishDto;
import gift.entity.Product;
import gift.service.WishService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/wish")
@Controller
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    //insert
    @PostMapping()
    public ResponseEntity<Map<String, Object>> insert(@RequestBody WishDto.Request request) {
        return wishService.insert(request);
    }

    //getAll
    @GetMapping()
    public Map<WishDto, Product> getAll(@RequestParam String token) {
        return wishService.getAll(token);
    }

    //delete one by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id, @RequestParam String token) {
        return wishService.delete(id, token);
    }
}

package gift.controller;

import gift.domain.MenuRequest;
import gift.domain.MenuResponse;
import gift.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuRestController {

    private final MenuService menuService;

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<String> save(
            @ModelAttribute @Valid MenuRequest request,
            BindingResult result
    ) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        else{
            menuService.save(request);
            return ResponseEntity.ok().body("successfully saved");
        }
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> read() {
        return ResponseEntity.ok().body(menuService.findall());
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute MenuRequest request
    ) {
        menuService.update(
                id,
                request
        );
        return ResponseEntity.ok().body("successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        menuService.delete(id);
        return ResponseEntity.ok().body("successfully deleted");
    }
}

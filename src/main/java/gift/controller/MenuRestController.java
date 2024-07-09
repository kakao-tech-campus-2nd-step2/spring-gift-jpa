package gift.controller;

import gift.domain.Menu;
import gift.domain.MenuRequest;
import gift.domain.MenuResponse;
import gift.service.MemberService;
import gift.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/menus")
public class MenuRestController {

    private final MenuService menuService;

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<Object> save(
            @ModelAttribute @Valid MenuRequest request,
            BindingResult result
    ) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        else{
            Menu menu =  menuService.save(request);
            return ResponseEntity.ok().body(menu);
        }
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> read() {
        return ResponseEntity.ok().body(menuService.findall());
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute MenuRequest request
    ) {
        Menu menu = menuService.update(id, request);
        return ResponseEntity.ok().body(menu);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        menuService.delete(id);
        return ResponseEntity.ok().body("successfully deleted");
    }
}

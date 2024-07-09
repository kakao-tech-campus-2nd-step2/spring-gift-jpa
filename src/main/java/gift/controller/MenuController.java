package gift.controller;

import gift.domain.MenuRequest;
import gift.domain.MenuResponse;
import gift.service.MenuService;
import gift.domain.Menu;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import org.springframework.ui.Model;

@Controller
@RequestMapping("/menus/view")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    public String returnView(
          String errorMsg,
          Model model){
        if(errorMsg != null){
            model.addAttribute("errors", errorMsg);
            model.addAttribute("menus", menuService.findall());
            return "Menu";
        }
        model.addAttribute("menus", menuService.findall());
        return "redirect:/menu";
    }

    @PostMapping
    public void save(
            @ModelAttribute @Valid MenuRequest request,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            returnView(result.getFieldError().getDefaultMessage(),model);
            return;
        }
        returnView(null,model);
    }

    @GetMapping
    public String read(Model model) {
        List<MenuResponse> menus = menuService.findall();
        model.addAttribute("menus", menus);
        return "Menu";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Menu menu = menuService.findById(id);
        model.addAttribute("menu", menu);
        return "update_menu";
    }

}

package gift.controller;

import gift.dto.ProductChangeRequestDto;
import gift.service.ProductService;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {
    private final ProductService productService;

    public WebController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String itemList(Model model) {
        List<ProductResponseDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "items";
    }

    @PostMapping("products/add")
    public String add(@Valid @ModelAttribute("requestDto") ProductRequestDto requestDto, BindingResult result) {
        if (result.hasErrors()) {
            return "addForm";
        }
        productService.addProduct(requestDto);
        return "redirect:/";
    }

    @GetMapping("products/add")
    public String add(Model model) {
        model.addAttribute("requestDto", new ProductRequestDto());
        return "addForm";
    }

    @GetMapping("products/edit/{id}")
    public String getEditForm(
            @PathVariable("id") Long id, Model model) {
        ProductResponseDto product = productService.findProduct(id);
        model.addAttribute("requestDto", product);
        model.addAttribute("id", id);
        return "editForm";
    }

    @PutMapping("products/edit/{id}")
    public String editProduct(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("requestDto") ProductChangeRequestDto requestDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return "editForm";
        }
        productService.editProduct(id, requestDto);
        return "redirect:/";
    }

    @DeleteMapping("products/delete/{id}")
    public String deleteProduct(
            @PathVariable("id") Long id
    ) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}

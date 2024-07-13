package gift.controller;

import gift.dto.request.ProductRequestDto;
import gift.dto.response.ProductResponseDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String getAll(Model model,
                         @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        List<ProductResponseDto> productDtos = productService.findProducts(pageable);
        model.addAttribute("productDtos", productDtos);
        return "manager";
    }

    @GetMapping("/new")
    public String addForm(){
        return "addForm";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute @Valid ProductRequestDto productDto){
        productService.addProduct(productDto);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id,
                           Model model){
        ProductResponseDto productDto = productService.findProductById(id);

        model.addAttribute("productDto", productDto);

        return "editForm";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute @Valid ProductRequestDto productDto){
        productService.updateProduct(id, productDto);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/products";

    }

}

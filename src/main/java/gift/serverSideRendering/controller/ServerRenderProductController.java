package gift.serverSideRendering.controller;

import gift.domain.service.ProductService;
import gift.domain.dto.ProductRequestDto;
import gift.domain.dto.ProductResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ServerRenderProductController {

    private final ProductService service;

    @Autowired
    public ServerRenderProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String showProducts(Model model) {
        List<ProductResponseDto> products = service.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequestDto", new ProductRequestDto("", 0L, ""));
        return "addProduct";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductRequestDto requestDto, Model model) {
        service.addProduct(requestDto);
        return "redirect:/products";
    }

    @GetMapping("/update/{id}")
    public String showUpdateProductForm(@PathVariable("id") Long id, Model model) {
        ProductResponseDto product = service.getProductById(id);
        ProductRequestDto dto = new ProductRequestDto(product.name(), product.price(), product.imageUrl());
        model.addAttribute("productRequestDto", dto);
        model.addAttribute("productId", id);
        return "updateProduct";
    }

    @PutMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute ProductRequestDto requestDto) {
        service.updateProductById(id, requestDto);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
        return "redirect:/products";
    }
}

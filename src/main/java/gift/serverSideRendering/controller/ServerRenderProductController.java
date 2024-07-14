package gift.serverSideRendering.controller;

import gift.domain.dto.request.ProductRequest;
import gift.domain.dto.response.ProductResponse;
import gift.domain.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
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

    public ServerRenderProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String showProducts(Model model) {
        List<ProductResponse> products = service.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequestDto", new ProductRequest("", 0, ""));
        return "addProduct";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductRequest requestDto, Model model) {
        service.addProduct(requestDto);
        return "redirect:/products";
    }

    @GetMapping("/update/{id}")
    public String showUpdateProductForm(@PathVariable("id") Long id, Model model) {
        ProductResponse product = ProductResponse.of(service.getProductById(id));
        ProductRequest dto = new ProductRequest(product.name(), product.price(), product.imageUrl());
        model.addAttribute("productRequestDto", dto);
        model.addAttribute("productId", id);
        return "updateProduct";
    }

    @PutMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute ProductRequest requestDto) {
        service.updateProductById(id, requestDto);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
        return "redirect:/products";
    }
}

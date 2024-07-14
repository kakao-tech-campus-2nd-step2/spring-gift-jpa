package gift.Controller;

import gift.Model.ProductDto;
import gift.Service.ProductService;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public String getAllProductsByRoot(Model model,
                                       @RequestParam(value="page", defaultValue="0") int page,
                                       @RequestParam(value="size", defaultValue="10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> paging = productService.getAllProductsByPage(pageable);
        model.addAttribute("paging", paging);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paging.getTotalPages());
        return "products";
    }

    @GetMapping("/products")
    public String getAllProductsByUser(Model model,
                              @RequestParam(value="page", defaultValue="0") int page,
                              @RequestParam(value="size", defaultValue="10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> paging = productService.getAllProductsByPage(pageable);
        model.addAttribute("paging", paging);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paging.getTotalPages());
        return "user_products";
    }

    @RequestMapping(value = "/api/products/create", method = {RequestMethod.GET, RequestMethod.POST})
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, HttpServletRequest request, Model model) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("product", new ProductDto());
            return "product_form";
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("product", productDto);
            productService.saveProduct(productDto);
            return "redirect:/api/products";
        }
        return "error";
    }

    @RequestMapping(value = "/api/products/update/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateProductById(@PathVariable Long id, @Valid @ModelAttribute ProductDto productDtoDetails, HttpServletRequest request, Model model) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            Optional<ProductDto> optionalProduct = productService.getProductById(id);
            model.addAttribute("product", optionalProduct.get());
            return "product_form";
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            productService.updateProduct(productDtoDetails);
            return "redirect:/api/products";
        }
        return "error";
    }

    @PostMapping("/api/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        Optional<ProductDto> optionalProduct = productService.getProductById(id);
        model.addAttribute("product", optionalProduct.get());
        productService.deleteProduct(id);
        return "redirect:/api/products";  // 제품 목록 페이지로 리디렉션
    }

}

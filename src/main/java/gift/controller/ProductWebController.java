package gift.controller;

import gift.dto.ProductDTO;
import gift.exception.ErrorCode;
import gift.exception.InvalidProductNameException;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/web/products")
public class ProductWebController {

    private final ProductService productService;
    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String getAllProducts(Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(defaultValue = "asc") String direction) {

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<ProductDTO> productPage = productService.getProducts(pageable);

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);

        return "productList";
    }

    @GetMapping("/detail/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetail"; // product detail view의 이름을 반환
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "addProduct"; // add product form view의 이름을 반환
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }
        validateProductName(productDTO.getName());
        productService.saveProduct(productDTO);
        return "redirect:/web/products/list";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "editProduct"; // edit product form view의 이름을 반환
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editProduct";
        }
        validateProductName(productDTO.getName());
        productService.updateProduct(id, productDTO);
        return "redirect:/web/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/web/products/list"; // 상품 목록 페이지로 리다이렉트
    }

    // 규칙 3가지
    private void validateProductName(String name) {
        if (name.length() > 20) {
            throw new InvalidProductNameException(ErrorCode.INVALID_NAME_LENGTH);
        }
        if (!Pattern.matches("[a-zA-Z0-9가-힣()\\[\\]+\\-&/_ ]*", name)) {
            throw new InvalidProductNameException(ErrorCode.INVALID_CHARACTERS);
        }
        if (name.contains("카카오")) {
            throw new InvalidProductNameException(ErrorCode.CONTAINS_KAKAO);
        }
    }
}


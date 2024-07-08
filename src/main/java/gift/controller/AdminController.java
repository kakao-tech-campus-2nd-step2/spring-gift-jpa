package gift.controller;

import gift.exception.ProductException;
import gift.model.dto.ProductRequestDto;
import gift.model.dto.ProductResponseDto;
import gift.repository.ProductDao;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class AdminController {

    private final ProductDao productDao;

    public AdminController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductResponseDto> productList = productDao.selectAllProduct()
            .stream()
            .map(ProductResponseDto::from)
            .toList();
        model.addAttribute("productList", productList);
        return "products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("productRequestDto", new ProductRequestDto());
        return "add-product-form";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductRequestDto productRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-product-form";
        }
        productDao.insertProduct(productRequestDto.toEntity());
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("productRequestDto",
            ProductRequestDto.from(productDao.selectProductById(id)));
        return "modify-product-form";
    }

    @PostMapping("edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
        @Valid @ModelAttribute ProductRequestDto productRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "modify-product-form";
        }
        productDao.updateProductById(id, productRequestDto.toEntity());
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productDao.deleteProductById(id);
        return "redirect:/admin/products";
    }

    @ExceptionHandler(ProductException.class)
    public String handleProductException(ProductException productException, Model model) {
        model.addAttribute("errorMessage", productException.getMessage());
        return "error";
    }

}

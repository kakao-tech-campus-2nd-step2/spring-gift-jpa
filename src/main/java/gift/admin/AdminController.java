package gift.admin;

import gift.product.ProductDto;
import gift.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;

    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/list")
    public String listProducts(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "list"; // list.html 파일 보여주기
    }

    @GetMapping("/products/view/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        ProductDto product = productService.findById(id);
        model.addAttribute("product", product);
        return "view"; // view.html 파일 보여주기
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "add"; // add.html 파일 보여주기
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add"; // 에러가 있으면 다시 add.html 보여주기
        }
        productService.save(productDto);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        ProductDto product = productService.findById(id);
        model.addAttribute("productDto", new ProductDto(product.id(), product.name(), product.price(), product.imgUrl()));
        return "edit";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, @Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return "edit"; // 에러가 있으면 다시 edit.html 보여주기
        }
        productService.update(id, productDto);
        return "redirect:/admin/products/list";
    }
}
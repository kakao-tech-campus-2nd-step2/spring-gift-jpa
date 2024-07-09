package gift.controller;

import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }
    //전체 상품목록
    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products-list";
    }
    //상품 추가폼 끌어오기
    @GetMapping("/add")
    public String addProductsForm(Model model) {
        model.addAttribute("product",new Product());
        return "addProducts-form";
    }
    //상품추가 Post
    @PostMapping("/add")
    public String addProduct(Model model, @Valid @ModelAttribute Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "addProducts-form";
        }
        if(productService.addProduct(product)!=-1L){
            return "redirect:/products";
        }
        model.addAttribute("error","이미존재하는 상품 id");
        return "addProducts-form";
    }

    //상품업데이트
    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> product= productService.findById(id);
        if(product.isPresent()){
            model.addAttribute("product",product);
            return "updateProducts-form";
        }
        return "redirect:/products";
    }

    @PutMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, Model model, @Valid @ModelAttribute Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "updateProducts-form";
        }
        if(productService.updateProduct(product)!=-1L){
            return "redirect:/products";
        }
        model.addAttribute("error","존재하지 않는 상품 id");
        return "updateProducts-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
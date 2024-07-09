package gift.controller;

import gift.domain.Product;
import gift.exception.ProductNotFoundException;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // 목록 페이지
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    // id 클릭 시 상품 상세보기
    @GetMapping("/{id}")
    public String findProductById(@PathVariable Long id,Model model){
        try{
            Product product = productService.findById(id);
            model.addAttribute("product",product);
            return "product";
        }catch(ProductNotFoundException e){
            return "products";
        }
    }

    // 수정
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model)
    {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "editForm";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,@Valid @ModelAttribute Product product)
    {
        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    // 추가
    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("product",new Product());
        return "addForm";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute Product product){
        productService.addProduct(product);
        return "redirect:/products";
    }

    // 삭제
    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}


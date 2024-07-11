package gift.controller;

import gift.model.Product;
import gift.service.ProductService;
import gift.service.ProductService.ProductServiceStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/products")
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product()); // 빈 Product 객체를 생성하여 모델에 추가
        return "create";
    }

    @PostMapping("/add")
    public ModelAndView addProduct(@ModelAttribute @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("create");
        }

        ProductServiceStatus response = productService.createProduct(product);
        if (response == ProductServiceStatus.SUCCESS) {
            return new ModelAndView("redirect:/admin/products");
        }

        ModelAndView mav = new ModelAndView("create");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("error", "상품 추가 실패");
        return mav;
    }

    @GetMapping("/update/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        return "update";
    }

    @PostMapping("/update/{id}")
    public ModelAndView editProduct(@PathVariable Long id, @ModelAttribute @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("update");
        }

        ProductServiceStatus response = productService.editProduct(id, product);
        if (response == ProductServiceStatus.SUCCESS) {
            return new ModelAndView("redirect:/admin/products");
        }

        ModelAndView mav = new ModelAndView("update");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("error", "상품 수정 실패");
        return mav;
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        ProductServiceStatus response = productService.deleteProduct(id);
        if (response == ProductServiceStatus.SUCCESS) {
            return "redirect:/admin/products"; // 성공적으로 삭제 후 목록 페이지로 리다이렉트
        }
        return "error"; // 실패 시 에러 페이지로 리다이렉트
    }
}

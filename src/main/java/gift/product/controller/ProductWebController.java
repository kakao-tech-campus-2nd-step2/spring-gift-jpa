package gift.product.controller;

import gift.product.model.HashMapProductRepository;
import gift.product.model.dto.CreateProductRequest;
import gift.product.model.dto.ProductResponse;
import gift.product.model.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductWebController {
    private final HashMapProductRepository productRepository = new HashMapProductRepository();
    private static final Logger logger = LoggerFactory.getLogger(ProductWebController.class);

    // 메인 페이지 (상품 목록 페이지)
    @GetMapping
    public String listProducts(Model model) {
        List<ProductResponse> products = productRepository.findAllProduct();
        logger.info("Loaded products: {}", products);
        System.out.println("Loaded products: " + products);
        model.addAttribute("products", products);
        return "products";
    }

    // 상품 추가 페이지
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("productReq", new CreateProductRequest());
        return "add_product";
    }

    @PostMapping
    public String saveProduct(@Valid @ModelAttribute CreateProductRequest productReq,
                              RedirectAttributes redirectAttributes) {
        logger.info("addProduct : {} {} {}", productReq.getName(), productReq.getPrice(), productReq.getImageUrl());
        productRepository.addProduct(productReq);
        redirectAttributes.addFlashAttribute("message", "새 상품이 추가되었습니다.");
        return "redirect:/product";
    }

    //상품 수정 페이지
    @GetMapping("/{id}/details")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        ProductResponse product = productRepository.findProduct(id);
        model.addAttribute("productReq", product);
        return "edit_product";
    }

    @PutMapping
    public String updateProduct(@ModelAttribute UpdateProductRequest updateProductRequest,
                                RedirectAttributes redirectAttributes) {
        productRepository.updateProduct(updateProductRequest);
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다.");
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteProduct(id);
        return "redirect:/product";
    }

}

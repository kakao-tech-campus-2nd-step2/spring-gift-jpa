package gift.product.controller;

import gift.product.error.NotFoundException;
import gift.product.model.Product;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //상품 전체 조회 페이지
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String showProductList(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products_list";
    }

    //상품 추가 폼 페이지
    @GetMapping("/new")
    public String createProductForm() {
        return "form";
    }

    //상품 추가 데이터 응답
    @PostMapping("/new")
    public String create(@Valid @ModelAttribute Product formProduct) {
        productService.addProduct(formProduct);
        return "redirect:/products";
    }

    //상품 단일 조회 기능
    @GetMapping("/{id}")
    public String showOneProduct(@PathVariable("id") Long id, Model model) {
        List<Product> products = new ArrayList<>();
        products.add(productService.getProductById(id));
        model.addAttribute("products", products);
        return "products_list";
    }

    //상품 검색 기능
    @GetMapping("/search")
    public String searchProduct(@Valid @ModelAttribute Product formProduct, Model model) {
        List<Product> products = productService.searchProduct(formProduct.getName());
        model.addAttribute("products", products);
        return "products_list";
    }

    //상품 삭제 기능
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new NotFoundException("해당 상품이 존재하지 않습니다.");
        }
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    //상품 수정 폼 페이지
    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new NotFoundException("해당 상품이 존재하지 않습니다.");
        }
        model.addAttribute("product", product);
        return "form";
    }

    //상품 수정 기능
    @PutMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute Product updateProduct) {
        productService.updateProduct(id, updateProduct);
        return "redirect:/products/" + id;
    }
}

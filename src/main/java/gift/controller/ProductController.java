package gift.controller;

import gift.domain.Product;
import gift.error.NotFoundException;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
    public String showProductList(
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
        Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = productService.getAllProducts(pageable);

        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("currentPage", productsPage.getNumber());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("totalItems", productsPage.getTotalElements());
        model.addAttribute("pageSize", productsPage.getSize());

        return "products_list";
    }

    //상품 추가 폼 페이지
    @GetMapping("/new")
    public String createProductForm() {
        return "form";
    }

    //상품 추가 데이터 응답
    @PostMapping
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
    @DeleteMapping("/{id}")
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
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") Long id,
        @Valid @ModelAttribute Product updateProduct) {
        productService.updateProduct(id, updateProduct);
        return "redirect:/products/" + id;
    }
}

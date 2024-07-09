package gift.Controller;

import gift.Model.Product;
import gift.Service.ProductService;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getAllProductsByRoot(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products")
    public String getAllProductsByUser(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "user_products";
    }

    @RequestMapping(value = "/api/products/create", method = {RequestMethod.GET, RequestMethod.POST})
    public String createProduct(@Valid @ModelAttribute Product product, HttpServletRequest request, Model model) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("product", new Product());
            return "product_form";
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("product", product);
            productService.saveProduct(product);
            return "redirect:/api/products";
        }
        return "error";
    }

    @RequestMapping(value = "/api/products/update/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateProductById(@PathVariable Long id, @Valid @ModelAttribute Product productDetails, HttpServletRequest request, Model model) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            Optional<Product> optionalProduct = productService.getProductById(id);
            model.addAttribute("product", optionalProduct.get());
            return "product_form";
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            productService.updateProduct(id, productDetails);
            return "redirect:/api/products";
        }
        return "error";
    }

    @PostMapping("/api/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        model.addAttribute("product", optionalProduct.get());
        productService.deleteProduct(id);
        return "redirect:/api/products";  // 제품 목록 페이지로 리디렉션
    }

}

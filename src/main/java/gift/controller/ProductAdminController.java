package gift.controller;

import gift.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final Map<Long, Product> products = new HashMap<>();  // 상품을 저장하는 Map
    private Long currentId = 1L;  // 새로운 상품의 ID를 생성하기 위한 변수

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", new ArrayList<>(products.values()));
        return "productList";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product(null, "", 0, ""));
        return "productForm";
    }

    @PostMapping("/add")
    public String addProduct(Product product) {
        Product newProduct = new Product(currentId++, product.name(), product.price(), product.imageUrl());
        products.put(newProduct.id(), newProduct);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = products.get(id);
        if (product == null) {
            throw new IllegalArgumentException("Invalid product Id:" + id);
        }
        model.addAttribute("product", product);
        return "productForm";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, Product product) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("Invalid product Id:" + id);
        }
        Product updatedProduct = new Product(id, product.name(), product.price(), product.imageUrl());
        products.put(id, updatedProduct);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        products.remove(id);
        return "redirect:/admin/products";
    }
}
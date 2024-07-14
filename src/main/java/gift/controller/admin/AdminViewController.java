package gift.controller.admin;

import gift.domain.Product;
import gift.DTO.ProductRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final RestTemplate restTemplate;

    public AdminViewController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping
    public String showProductManagementPage(Model model) {
        try {
            List<Product> products = restTemplate.getForObject("http://localhost:8080/api/products",
                List.class);
            if (products == null) {
                model.addAttribute("products", List.of());
                return "productManagement"; // 템플릿 파일 이름
            }
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load products: " + e.getMessage());
        }
        return "productManagement"; // admin 템플릿 파일 이름
    }

    @GetMapping("/form")
    public String showProductForm(
        @RequestParam(required = false) Long id,
        Model model
    ) {
        if (id == null) {
            model.addAttribute("product", new ProductRequest("", 0, ""));
            model.addAttribute("isEditing", false);
            return "productForm"; // 폼 템플릿 파일
        }

        try {
            Product product = restTemplate.getForObject("http://localhost:8080/api/products/" + id,
                Product.class);
            model.addAttribute("product", product);
            model.addAttribute("isEditing", true);
        } catch (HttpClientErrorException e) {
            model.addAttribute("error", "Product not found: " + e.getMessage());
            return "redirect:/admin";
        }

        return "productForm"; // 폼 템플릿 파일
    }

    @PostMapping("/form")
    public String saveProduct(
        @Valid @ModelAttribute Product product,
        BindingResult bindingResult,
        @RequestParam(required = false, name = "_method")
        String method,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditing", isUpdateMethod(method));
            return "productForm";
        }
        if (isUpdateMethod(method)) {
            return updateProduct(product, model);
        }
        return createProduct(product, model);
    }

    private boolean isUpdateMethod(String method) {
        return "put".equalsIgnoreCase(method);
    }

    private String updateProduct(Product product, Model model) {
        try {
            restTemplate.put("http://localhost:8080/api/products/" + product.getId(), product);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to save product: " + e.getMessage());
            return "productForm";
        }
        return "redirect:/admin";
    }

    private String createProduct(Product product, Model model) {
        try {
            restTemplate.postForObject("http://localhost:8080/api/products", product,
                Product.class);
        } catch (HttpClientErrorException.BadRequest e) {
            model.addAttribute("error", "Product ID already exists: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to save product: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        restTemplate.delete("http://localhost:8080/api/products/" + id);
        return "redirect:/admin";
    }
}

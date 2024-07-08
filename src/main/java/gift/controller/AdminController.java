package gift.controller;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository repository;

    public AdminController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public String adminPage(Model model) {
        model.addAttribute("products", repository.findAll());
        return "admin";
    }

    @GetMapping("/add")
    public String getAdminAddPage() {
        return "adminAdd";
    }

    @GetMapping("/deleted")
    public String getAdminDeletedPage() {
        return "adminDeleted";
    }

    @GetMapping("/{id}")
    public String getAdminAddPage(@PathVariable("id") Long id, Model model) {
        Product product = repository.findById(id);
        if (product == null) return "error";
        model.addAttribute("product", product);
        return "adminProductDetail";
    }
}

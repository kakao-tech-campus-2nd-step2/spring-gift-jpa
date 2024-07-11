package gift.controller.page;

import gift.entity.ProductRecord;
import gift.repository.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {
    private final ProductDAO productDAO;

    AdminController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/")
    public String admin(Model model) {
        List<ProductRecord> products = productDAO.getAllRecords();

        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/products/{id}/edit")
    public String editProduct(@PathVariable int id, Model model) {
        ProductRecord product = productDAO.getRecord(id);
        model.addAttribute("product", product);

        return "product_edit";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model) {
        return "product_add";
    }
}

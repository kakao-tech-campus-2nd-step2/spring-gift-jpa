package gift.controller;

import gift.model.Product;
import gift.dao.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    private final ProductDao ProductDao;

    public IndexController(gift.dao.ProductDao productDao) {
        ProductDao = productDao;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/postform")
    public String postform(){
        return "postform";
    }

    @PostMapping("/editform/{id}")
    public String editform(@PathVariable Long id, Model model){
        Product product = ProductDao.selectProduct(id);
        model.addAttribute("product", product);
        return "editform";
    }
}

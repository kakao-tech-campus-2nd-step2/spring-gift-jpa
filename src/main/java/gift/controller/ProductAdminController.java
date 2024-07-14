package gift.controller;


import gift.dto.product.ProductWithOptionDTO;
import gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ProductAdminController {
    @Autowired
    private ProductService productService;

    @GetMapping("/admin/products")

    public ModelAndView adminProducts(Model model, @RequestParam(value = "page", defaultValue = "0") int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 2, Sort.by(Sort.Direction.ASC, "id"));
        Page<ProductWithOptionDTO> products = productService.getAllProductsWithOption(pageable);
        model.addAttribute("products", products);
        return new ModelAndView("admin/products");

    }

    @GetMapping("/admin/add")
    public String adminProductsAdd(Model model) {
        return ("admin/add");
    }

    @GetMapping("/admin/modify")
    public String adminProductsModify(Model model) {
        return ("admin/modify");
    }

    @GetMapping("/admin/delete")
    public String adminProductsDelete(Model model) {
        return ("admin/delete");
    }
}

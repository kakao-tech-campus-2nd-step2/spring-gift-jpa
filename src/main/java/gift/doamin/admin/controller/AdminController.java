package gift.doamin.admin.controller;

import gift.doamin.product.entity.Product;
import gift.doamin.product.repository.ProductRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

public class AdminController {
    ProductRepository productRepository;

    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/admin")
    public ModelAndView showAdminPage(@RequestParam(defaultValue = "1") int page) {
        List<Product> products = productRepository.findAll();
        int lastPage = (products.size() - 1) / 5 + 1;
        if (page < 1 || page > lastPage) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/index");
        modelAndView.addObject("products",
            products.subList(Math.max(0, page * 5 - 5), Math.min(page * 5, products.size())));
        modelAndView.addObject("productsCnt", products.size());
        modelAndView.addObject("page", page);
        modelAndView.addObject("startPage", Math.max(1, page - 2));
        modelAndView.addObject("endPage", Math.max(lastPage, page + 2));
        modelAndView.addObject("lastPage", lastPage);
        return modelAndView;
    }
}

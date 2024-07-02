package gift.main.controller;

import gift.main.dto.ProductRequest;
import gift.main.entity.Product;
import gift.main.handler.ProductTransformer;
import gift.main.repository.ProductDao;
import gift.main.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
//    private final ProductDao productDao;
    private final ProductService productService;


    public AdminController(ProductDao productDao, ProductService productService) {
        this.productService = productService;
        productDao.createProductTable();
    }


    @GetMapping({"","/"})
    public String adminPage(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "product";
    }


    @GetMapping("/product")
    public String findProduct(@RequestParam(value = "id") long id,Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("seletProduct", product);
        model.addAttribute("products", productService.getProducts());
        return "product";

    }

    @PostMapping("/product")
    public String addProduct(@ModelAttribute ProductRequest productRequest,Model model) {
        productService.addProduct(productRequest);
        model.addAttribute("products", productService.getProducts());
        return "product";
    }

    @PutMapping(value = "/product")
    public String updateProduct(@RequestParam(value = "id") long id, @ModelAttribute ProductRequest productRequest, Model model){
        productService.updateProduct(id,productRequest);
        model.addAttribute("products", productService.getProducts());
        return "product";
    }

    @DeleteMapping("/product")
    public String deleteProduct(@RequestParam(value = "id") long id, Model model) {
        productService.deleteProduct(id);
        model.addAttribute("products", productService.getProducts());
        return "product";
    }



    @GetMapping("/product/edit")
    public String editPage(@RequestParam(value = "id") long id, @ModelAttribute ProductRequest productRequest,Model model) {
        Product product = ProductTransformer.convertToProduct(id, productRequest);
        model.addAttribute("product", product);
        return "edit";
    }

}

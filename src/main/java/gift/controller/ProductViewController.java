package gift.controller;

import gift.exception.NameException;
import gift.dto.ProductDto;
import gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ProductViewController {
    private final ProductService productService;

    @Autowired
    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("products",  productService.findAll());
        return "index";
    }

    @GetMapping("/create-product")
    public String createPage(){
        return "create";
    }

    @PostMapping("/create-product")
    public String create(@ModelAttribute ProductDto productDto){

        productService.create(productDto);
        return "redirect:/";
    }

    @GetMapping("/update-product/{id}")
    public String updatePage(@PathVariable("id") Long id, Model model){
        model.addAttribute("id", id);
        return "update";
    }

    @PostMapping("/update-product/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute ProductDto productDto){

        productService.update(id, productDto);
        return "redirect:/";
    }

    @GetMapping("/delete-product/{id}")
    public String delete(@PathVariable("id") Long id){
        productService.delete(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NameException.class)
    public String handleNameException(NameException e, Model model){
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}

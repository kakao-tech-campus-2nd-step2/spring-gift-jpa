package gift.controller;

import gift.form.ProductAddForm;
import gift.form.ProductUpdateForm;
import gift.model.Product;
import gift.repository.ProductDao;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductViewController {

    private final ProductDao productDao;

    @Autowired
    public ProductViewController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/step2/products")
    public String getAllProducts(Model model) {
        List<Product> productsList = productDao.getAllProducts();
        model.addAttribute("products", productsList);
        return "products";
    }

    @GetMapping("/step2/products/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        return "addForm";
    }

    @PostMapping("/step2/products/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductAddForm form,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addForm";
        }

        Product product = new Product(form.getName(), form.getPrice(), form.getImageUrl());
        productDao.insertProduct(product);
        return "redirect:/step2/products";
    }

    @GetMapping("/step2/products/edit")
    public String editForm(@RequestParam Long id, Model model) {
        model.addAttribute("product", productDao.getProductById(id));
        return "editForm";
    }

    @PostMapping("/step2/products/edit")
    public String editProduct(@Valid @ModelAttribute("product") ProductUpdateForm form,
        BindingResult bindingResult) {
        System.out.println("getId() = " + form.getId());
        if (bindingResult.hasErrors()) {
            return "editForm";
        }
        Product updatedProduct = productDao.getProductById(form.getId());
        updatedProduct.setName(form.getName());
        updatedProduct.setPrice(form.getPrice());
        updatedProduct.setImageUrl(form.getImageUrl());
        productDao.updateProduct(updatedProduct);
        return "redirect:/step2/products";
    }

    @PostMapping("/step2/products/delete")
    public String deleteProduct(@RequestParam("id") Long id) {
        productDao.deleteProduct(id);
        return "redirect:/step2/products";
    }

}

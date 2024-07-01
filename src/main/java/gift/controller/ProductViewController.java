package gift.controller;

import gift.model.Product;
import gift.repository.ProductDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String addForm() {
        return "addForm";
    }

    @PostMapping("/step2/products/add")
    public String addProduct(@RequestParam String name, @RequestParam int price,
        @RequestParam String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        productDao.insertProduct(product);
        return "redirect:/step2/products";
    }

    @GetMapping("/step2/products/edit")
    public String editForm(@RequestParam Long id, Model model) {
        model.addAttribute("product", productDao.getProductById(id));
        return "editForm";
    }

    @PostMapping("/step2/products/edit")
    public String editProduct(@RequestParam Long id, @RequestParam String name,
        @RequestParam int price, @RequestParam String imageUrl) {
        Product updatedProduct = productDao.getProductById(id);
        updatedProduct.setName(name);
        updatedProduct.setPrice(price);
        updatedProduct.setImageUrl(imageUrl);
        productDao.updateProduct(updatedProduct);
        return "redirect:/step2/products";
    }

    @PostMapping("/step2/products/delete")
    public String deleteProduct(@RequestParam("id") Long id) {
        productDao.deleteProduct(id);
        return "redirect:/step2/products";
    }

}

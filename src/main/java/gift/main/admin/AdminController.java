package gift.main.admin;

import gift.main.dto.ListProductResponse;
import gift.main.dto.ProductRequest;
import gift.main.dto.Response;
import gift.main.dto.SingleProductResponse;
import gift.main.entity.Product;
import gift.main.handler.MapToProductTransformer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Controller
public class AdminController {
    private final Map<Long, Product> producstRepository = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();


    @PostConstruct
    public void init() {
        long id = idGenerator.incrementAndGet();
        Product product1 = new Product(id, "test", 300, "url");
        producstRepository.put(id, product1);
    }

    @GetMapping({"/admin"})
    public String adminPage(Model model) {
        model.addAttribute("products", getProduct());
        return "product";
    }


    @GetMapping("/admin/product")
    public String provideProduct(@RequestParam(value = "id") long id,Model model) {
        if (!producstRepository.containsKey(id)) {
            model.addAttribute("messages","해당아이디값은 없습니다.");
            model.addAttribute("products", getProduct());
            return "product";
        }
        model.addAttribute("seletProduct", producstRepository.get(id));
        model.addAttribute("products", getProduct());
        return "product";

    }

    @PostMapping("/admin/product")
    public String addProduct(@ModelAttribute ProductRequest productRequest,Model model) {
        long id = idGenerator.incrementAndGet();
        producstRepository.put(id, MapToProductTransformer.convertToProduct(id, productRequest));

        model.addAttribute("products", getProduct());
        return "product";
    }

    @GetMapping("/admin/product/edit")
    public String editPage(@RequestParam(value = "id") long id, @RequestParam(value = "name") String name, @RequestParam(value = "price") int price, @RequestParam(value = "imageUrl") String imageUrl, Model model) {

        Product product = new Product(id, name, price, imageUrl);
        model.addAttribute("product", product);

        return "edit";

    }

    //http://localhost:8080/spring-gift/admin/product/admin/product?id=1
    @PutMapping(value = "/admin/product")
    public String updateProduct(@RequestParam(value = "id") long id, @ModelAttribute ProductRequest productRequest, Model model){
        System.out.println("id = " + id);
        System.out.println("productRequest = " + productRequest);
        producstRepository.replace(id, MapToProductTransformer.convertToProduct(id, productRequest));
        model.asMap().clear();
        model.addAttribute("products", getProduct());
        return "product";
    }

    @DeleteMapping("/admin/product")
    public String deleteProduct(@RequestParam(value = "id") long id, Model model) {
        producstRepository.remove(id);
        model.addAttribute("products", getProduct());
        return "product";
    }

    private List<Product> getProduct() {
        List<Product> productList = new ArrayList<>();
        for (Map.Entry<Long, Product> productEntry : producstRepository.entrySet()) {
            productList.add(productEntry.getValue());
        }

        return productList;
    }

}

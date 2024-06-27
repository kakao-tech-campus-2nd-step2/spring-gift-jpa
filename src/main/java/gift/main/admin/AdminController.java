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
@RequestMapping("/admin")
public class AdminController {
    private final Map<Long, Product> productsRepository = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();


//    @PostConstruct
    public void init() {
        long id = idGenerator.incrementAndGet();
        Product product1 = new Product(id, "test", 300, "url");
        productsRepository.put(id, product1);
    }

    @GetMapping({"","/"})
    public String adminPage(Model model) {
        model.addAttribute("products", getProduct());
        return "product";
    }


    @GetMapping("/product")
    public String findProduct(@RequestParam(value = "id") long id,Model model) {
        if (!productsRepository.containsKey(id)) {
            model.addAttribute("messages","해당아이디값은 없습니다.");
            model.addAttribute("products", getProduct());
            return "product";
        }
        model.addAttribute("seletProduct", productsRepository.get(id));
        model.addAttribute("products", getProduct());
        return "product";

    }

    @PostMapping("/product")
    public String addProduct(@ModelAttribute ProductRequest productRequest,Model model) {
        long id = idGenerator.incrementAndGet();
        productsRepository.put(id, MapToProductTransformer.convertToProduct(id, productRequest));

        model.addAttribute("products", getProduct());
        return "product";
    }

    @GetMapping("/product/edit")
    public String editPage(@RequestParam(value = "id") long id, @ModelAttribute ProductRequest productRequest,Model model) {
        Product product = MapToProductTransformer.convertToProduct(id, productRequest);
        model.addAttribute("product", product);
        return "edit";
    }

    @PutMapping(value = "/product")
    public String updateProduct(@RequestParam(value = "id") long id, @ModelAttribute ProductRequest productRequest, Model model){
        productsRepository.replace(id, MapToProductTransformer.convertToProduct(id, productRequest));
        model.addAttribute("products", getProduct());
        return "product";
    }

    @DeleteMapping("/product")
    public String deleteProduct(@RequestParam(value = "id") long id, Model model) {
        productsRepository.remove(id);
        model.addAttribute("products", getProduct());
        return "product";
    }

    private List<Product> getProduct() {
        List<Product> productList = new ArrayList<>();
        for (Map.Entry<Long, Product> productEntry : productsRepository.entrySet()) {
            productList.add(productEntry.getValue());
        }

        return productList;
    }

}

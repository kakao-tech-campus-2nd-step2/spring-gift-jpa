package gift.main.admin;

import gift.main.dto.ProductRequest;
import gift.main.entity.Product;
import gift.main.handler.ProductTransformer;
import gift.main.repository.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductDao productDao;
    private final AtomicLong idGenerator = new AtomicLong();

    public AdminController(ProductDao productDao) {
        this.productDao = productDao;
        productDao.createProductTable();
    }


    @GetMapping({"","/"})
    public String adminPage(Model model) {
        model.addAttribute("products", productDao.selectProductAll());
        return "product";
    }


    @GetMapping("/product")
    public String findProduct(@RequestParam(value = "id") long id,Model model) {
        if (productDao.selectProduct(id)==null) {
            model.addAttribute("messages","해당아이디값은 없습니다.");
            model.addAttribute("products",productDao.selectProductAll());
            return "product";
        }
        model.addAttribute("seletProduct", productDao.selectProduct(id));
        model.addAttribute("products", productDao.selectProductAll());
        return "product";

    }

    @PostMapping("/product")
    public String addProduct(@ModelAttribute ProductRequest productRequest,Model model) {
        long id = idGenerator.incrementAndGet();
        productDao.insertProduct(ProductTransformer.convertToProduct(id, productRequest));

        model.addAttribute("products",  productDao.selectProductAll());
        return "product";
    }

    @PutMapping(value = "/product")
    public String updateProduct(@RequestParam(value = "id") long id, @ModelAttribute ProductRequest productRequest, Model model){
        productDao.updateProduct(id, ProductTransformer.convertToProduct(id, productRequest));
        model.addAttribute("products", productDao.selectProductAll());
        return "product";
    }

    @DeleteMapping("/product")
    public String deleteProduct(@RequestParam(value = "id") long id, Model model) {
        productDao.deleteProduct(id);
        model.addAttribute("products", productDao.selectProductAll());
        return "product";
    }

//    private List<Product> getProduct() {
//        List<Product> productList = new ArrayList<>();
//        for (Map.Entry<Long, Product> productEntry : productsRepository.entrySet()) {
//            productList.add(productEntry.getValue());
//        }
//
//        return productList;
//    }

    @GetMapping("/product/edit")
    public String editPage(@RequestParam(value = "id") long id, @ModelAttribute ProductRequest productRequest,Model model) {
        Product product = ProductTransformer.convertToProduct(id, productRequest);
        model.addAttribute("product", product);
        return "edit";
    }

}

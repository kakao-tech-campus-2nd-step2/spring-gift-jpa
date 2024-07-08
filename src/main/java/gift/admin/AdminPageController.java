package gift.admin;

import static gift.admin.AdminPageConfigure.MAX_PAGE_INDEX;
import static gift.admin.AdminPageConfigure.PAGE_SIZE;

import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminPageController {

    private final ProductRepository productRepository;

    public AdminPageController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/admin")
    public String adminPage(Model model, @RequestParam("page") Integer currentPage) {
        List<Product> totalProducts = productRepository.getAllProducts();
        List<Product> subProducts = totalProducts.subList((currentPage - 1) *
                PAGE_SIZE.getValue(),
            Math.min(currentPage * PAGE_SIZE.getValue(), totalProducts.size()));

        Integer totalProductsSize = totalProducts.size();

        model.addAttribute("products", subProducts);
        model.addAttribute("page", currentPage);
        model.addAttribute("totalProductsSize", totalProductsSize);
        model.addAttribute("currentPageProductSize", subProducts.size());
        model.addAttribute("pageList", getPageListRange(totalProductsSize,
            currentPage));

        return "adminPage";
    }

    private List<Integer> getPageListRange(Integer totalProductsSize, Integer page) {
        Integer totalPage = Math.ceilDiv(totalProductsSize, PAGE_SIZE.getValue());
        Integer endPage = Math.max(totalPage, 1); // endPage can't be 0

        // 내림 연산이 반드시 필요하기에, 약분을 통해 나누면 안된다.
        Integer startPage = (Math.floorDiv(page - 1, MAX_PAGE_INDEX.getValue()) + 1)
            * MAX_PAGE_INDEX.getValue()
            - (MAX_PAGE_INDEX.getValue() - 1);

        return IntStream.rangeClosed(
                startPage,
                Math.min(startPage + (MAX_PAGE_INDEX.getValue() - 1), endPage))
            .boxed().toList();
    }
}

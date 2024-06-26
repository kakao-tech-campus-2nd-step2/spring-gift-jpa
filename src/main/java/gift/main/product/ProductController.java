package gift.main.product;

import gift.main.dto.ListProductResponse;
import gift.main.dto.Response;
import gift.main.dto.SingleProductResponse;
import gift.main.entity.Product;
import gift.main.handler.MapToProductTransformer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    private final Map<Long, Product> producstRepository = new HashMap<>();

    @GetMapping("/products")
    private Response provideProductAll() {

        if (producstRepository.isEmpty()) {
            return new Response(200, "현재 모든 물건이 비어있습니다.");
        }

        List<Product> productList = new ArrayList<>();
        for (Map.Entry<Long, Product> productEntry : producstRepository.entrySet()) {
            productList.add(productEntry.getValue());
        }

        return new ListProductResponse(202, "ok", productList);
    }

    @GetMapping("/product")
    private Response provideProduct(@RequestParam(value = "id",defaultValue = "0x7fffffff") int idInt) {
        if (idInt == 0x7fffffff) {
            return new Response(400, "쿼리가 빠졌습니다.");
        }
        Long id = Long.valueOf(idInt);
        Product product = producstRepository.get(id);

        if (product == null) {
            return new Response(400, "해당 ID 값은 없습니다.");
        }

        return new SingleProductResponse(200, "ok", product);

    }

    @PostMapping("/product")
    private Response AddProduct(@RequestBody HashMap<String, Object> map) {
        Long id = Long.valueOf((int)map.get("id"));
        if (producstRepository.containsKey(id)){
            return new Response(400, "해당 id값은 이미 존재하는 값입니다.");
        }
        try {
            producstRepository.put(id, MapToProductTransformer.convertMapToProduct(map));
        } catch (NullPointerException e) {
            return new Response(400, "JSON 형식에 문제가 있습니다.");
        } catch (Exception e) {
            return new Response(500, "오류가 발생했습니다.");
        }
        return new Response(200, "정상등록되었습니다.");
    }

    @PutMapping("/product")
    private Response editProduct(@RequestBody HashMap<String, Object> map) {
        Long id = Long.valueOf((int)map.get("id"));
        if (!producstRepository.containsKey(id)){
            return new Response(400, "해당 id값은 존재하지 않습니다.");
        }
        try {
            producstRepository.replace(id, MapToProductTransformer.convertMapToProduct(map));
        } catch (NullPointerException e) {
            return new Response(400, "JSON 형식에 문제가 있습니다.");
        } catch (Exception e) {
            return new Response(500, "오류가 발생했습니다.");
        }
        return new Response(200, "정상등록되었습니다.");
    }

    @DeleteMapping("/product")
    private Response deleteProduct(@RequestParam(value = "id",defaultValue = "0x7fffffff") int idInt) {
        if (idInt == 0x7fffffff) {
            return new Response(400, "쿼리가 빠졌습니다.");
        }
        Long id = Long.valueOf(idInt);
        if (!producstRepository.containsKey(id)) {
            return new Response(400, "해당 id값은 존재하지 않습니다.");
        }
        producstRepository.remove(id);


        return new Response(200, "삭제가 처리되었습니다.");
    }

}

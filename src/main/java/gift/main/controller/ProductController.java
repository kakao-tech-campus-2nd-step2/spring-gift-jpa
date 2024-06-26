package gift.main.controller;

import gift.main.dto.ListProductResponse;
import gift.main.dto.ProductRequest;
import gift.main.dto.Response;
import gift.main.dto.SingleProductResponse;
import gift.main.entity.Product;
import gift.main.handler.MapToProductTransformer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ProductController {
    private final Map<Long, Product> producstRepository = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @GetMapping("/products")
    public Response provideProductAll() {
        if (producstRepository.isEmpty()) {
            return new Response(200, "현재 모든 물건이 비어있습니다.");
        }

        List<Product> productList = new ArrayList<>();
        for (Map.Entry<Long, Product> productEntry : producstRepository.entrySet()) {
            productList.add(productEntry.getValue());
        }

        return new ListProductResponse(200, "ok", productList);
    }

    @GetMapping("/product")
    public Response provideProduct(@RequestParam(value = "id") long id) {
        if (!producstRepository.containsKey(id)) {
            return new Response(400, "해당 ID 값은 없습니다.");
        }
        System.out.println("producstRepository.get(id) = " + producstRepository.get(id));
        return new SingleProductResponse(200, "ok", producstRepository.get(id));

    }

    @PostMapping("/product")
    public Response addProduct(@RequestBody ProductRequest productRequest) {
        long id = idGenerator.incrementAndGet();
        producstRepository.put(id, MapToProductTransformer.convertToProduct(id,productRequest));
        return new Response(200, "정상등록되었습니다.");
    }

    @PutMapping("/product")
    public Response updateProduct(@RequestParam(value = "id") long id, @RequestBody ProductRequest productRequest) {
        if (!producstRepository.containsKey(id)) {
            return new Response(400, "해당 id값은 존재하지 않습니다.");
        }
        producstRepository.replace((long) id, MapToProductTransformer.convertToProduct(id, productRequest));

        return new Response(200, "정상 수정되었습니다.");
    }

    @DeleteMapping("/product")
    public Response deleteProduct(@RequestParam(value = "id") long id) {
        if (!producstRepository.containsKey(id)) {
            return new Response(400, "해당 id값은 존재하지 않습니다.");
        }
        producstRepository.remove(id);

        return new Response(200, "삭제처리되었습니다.");
    }


}

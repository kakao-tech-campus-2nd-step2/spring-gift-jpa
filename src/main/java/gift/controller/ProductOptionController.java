package gift.controller;

import gift.dto.ProductOptionRequest;
import gift.dto.ProductOptionResponse;
import gift.service.ProductOptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/options")
public class ProductOptionController {

    private final ProductOptionService optionService;

    public ProductOptionController(ProductOptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addOption(@Valid @RequestBody ProductOptionRequest productOptionRequest) {
        var option = optionService.addOption(productOptionRequest);
        return ResponseEntity.created(URI.create("/api/options/" + option.id())).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateOption(@PathVariable Long id, @Valid @RequestBody ProductOptionRequest productOptionRequest) {
        optionService.updateOption(id, productOptionRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOptionResponse> getOption(@PathVariable Long id) {
        var option = optionService.getOption(id);
        return ResponseEntity.ok(option);
    }

    @GetMapping
    public ResponseEntity<List<ProductOptionResponse>> getOptions(@RequestParam Long productId) {
        var options = optionService.getOptions(productId);
        return ResponseEntity.ok(options);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        optionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}

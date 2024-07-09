package gift.service;

import gift.dto.WishDto;
import gift.entity.Product;
import gift.repository.WishRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WishService {
    private WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public ResponseEntity<Map<String, Object>> insert(WishDto.Request request) {
        return ResponseEntity.ok()
                .header("Authorization", "Basic" + request.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(wishRepository.save(request.getProductId(), request.getToken()));
    }

    public Map<WishDto, Product> getAll(String token) {
        return wishRepository.getAll(token);
    }

    public ResponseEntity<Map<String, Object>> delete(Long id, String token) {
        return wishRepository.delete(id, token);
    }
}

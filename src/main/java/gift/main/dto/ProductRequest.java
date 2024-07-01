package gift.main.dto;

import java.util.Objects;

public record ProductRequest(String name, int price, String imageUrl) {
}

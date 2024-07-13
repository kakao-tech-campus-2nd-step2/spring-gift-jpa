package gift.intergrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.exception.ErrorCode;
import gift.model.item.Item;
import gift.model.item.ItemForm;
import gift.model.user.User;
import gift.repository.ItemRepository;
import gift.repository.WishListRepository;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WishListServiceIntergrationTest {

    @LocalServerPort
    private int port;
    private String host = "http://localhost:";
    private final User testUser = new User(1L, "test@test", "test");
    private final Item testItem = new Item(1L, "ItemA", 1000L, "imgUrl");

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void Setup() {
        wishListRepository.deleteAll();
        itemRepository.deleteAll();
    }

    HttpHeaders initForToken() {
        ResponseEntity<String> result = restTemplate.postForEntity(
            host + port + "/login", testUser, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(result.getBody());
        return headers;
    }

    Long initItemId() {
        return itemRepository.save(testItem).getId();
    }

    @DisplayName("wishList 추가 -> 조회 성공 테스트")
    @Test
    void testAdd_FindSuccess() {
        Long itemId = initItemId();
        HttpHeaders headers = initForToken();
        RequestEntity<?> request1 = new RequestEntity<>(headers, HttpMethod.POST,
            URI.create(host + port + "/wishes/" + itemId));
        RequestEntity<?> request2 = new RequestEntity<>(headers, HttpMethod.GET,
            URI.create(host + port + "/wishes"));

        ResponseEntity<?> response1 = restTemplate.exchange(request1, String.class);
        ResponseEntity<String> response2 = restTemplate.exchange(request2, String.class);

        assertAll(
            () -> assertThat(response1.getStatusCode()).as("wishList 추가").isEqualTo(HttpStatus.OK),
            () -> assertThat(response2.getBody()).as("wishlist 조회").contains(testItem.getName())
        );
    }

    @DisplayName("존재하지 않는 item wishList 추가 시도 테스트")
    @Test
    void testAddNotFoundItem() {
        HttpHeaders headers = initForToken();
        RequestEntity<?> request1 = new RequestEntity<>(headers, HttpMethod.POST,
            URI.create(host + port + "/wishes/100"));

        ResponseEntity<String> response1 = restTemplate.exchange(request1, String.class);

        assertAll(
            () -> assertThat(response1.getStatusCode()).as("wishlist 추가 실패").isEqualTo(
                ErrorCode.ITEM_NOT_FOUND.getStatus()),
            () -> assertThat(response1.getBody()).contains(ErrorCode.ITEM_NOT_FOUND.getMessage())
        );
    }

    @DisplayName("WishList에 item 추가 후 itemRepository에서 해당 item 삭제 -> WishList 조회")
    @Test
    void testItemDeleteAndCascadeSuccess() {
        Long itemId = initItemId();
        HttpHeaders headers = initForToken();
        RequestEntity<?> request1 = new RequestEntity<>(headers, HttpMethod.POST,
            URI.create(host + port + "/wishes/" + itemId));
        RequestEntity<?> request2 = new RequestEntity<>(headers, HttpMethod.DELETE,
            URI.create(host + port + "/product/" + itemId));
        RequestEntity<?> request3 = new RequestEntity<>(headers, HttpMethod.GET,
            URI.create(host + port + "/wishes"));

        ResponseEntity<?> response1 = restTemplate.exchange(request1, String.class);
        restTemplate.exchange(request2, String.class);
        ResponseEntity<String> response2 = restTemplate.exchange(request3, String.class);

        assertAll(
            () -> assertThat(response2.getBody()).as("wishlist 조회")
                .doesNotContain(testItem.getName())
        );
    }

    @DisplayName("WishList에 item 추가 후 해당 item 수정 -> WishList 조회")
    @Test
    void testItemUpdateAndCascadeSuccess() {
        Long itemId = initItemId();
        HttpHeaders headers = initForToken();
        ItemForm updateData = new ItemForm("updatedName", 2000L, "imgUrl");

        RequestEntity<?> request1 = new RequestEntity<>(headers, HttpMethod.POST,
            URI.create(host + port + "/wishes/" + itemId));

        RequestEntity<ItemForm> request2 = RequestEntity.put(host + port + "/product/" + itemId)
            .accept(
                MediaType.APPLICATION_JSON).headers(headers).body(updateData);

        RequestEntity<?> request3 = new RequestEntity<>(headers, HttpMethod.GET,
            URI.create(host + port + "/wishes"));

        ResponseEntity<?> response1 = restTemplate.exchange(request1, String.class);
        restTemplate.exchange(request2, String.class);
        ResponseEntity<String> response2 = restTemplate.exchange(request3, String.class);

        assertAll(
            () -> assertThat(response2.getBody()).as("wishlist 조회(변경 전 이름)")
                .doesNotContain(testItem.getName()),
            () -> assertThat(response2.getBody()).as("wishlist 조회(변경 후 이름)")
                .contains(updateData.getName())
        );
    }
}

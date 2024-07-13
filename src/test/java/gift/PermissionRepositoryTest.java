package gift;

import gift.permission.repository.PermissionRepository;
import gift.user.entity.User;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PermissionRepositoryTest {

    private PermissionRepository permissionRepository;

    @Autowired
    public PermissionRepositoryTest(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Test
    public void createTest() {
        User user1 = new User("kangwlgns@daum.net", "aaaaa11111");
        User user2 = new User("kangwlgns@daum.net", "qwer1234");
        User nullUser = new User(null, null);

        User actual = permissionRepository.save(user1);

        // 주소값을 비교했을 때 동일해야 함. (동일성 보장)
        Assertions.assertThat(actual == user1).isTrue();
        // id가 JPA의 생성전략에 의해 생성된 상태여야 함.
        Assertions.assertThat(actual.getUserId()).isNotNull();
        // 동일한 이메일로 한번 더 생성을 요청하면 UNIQUE 제약 조건에 위배되어야 함.
        Assertions.assertThatCode(() -> {
            permissionRepository.save(user2);
        }).isInstanceOf(Exception.class);
        // null을 넣으면 null 제약 조건에 위배 되어야 함.
        Assertions.assertThatCode(() -> {
            permissionRepository.save(nullUser);
        }).isInstanceOf(Exception.class);
    }

    @Test
    public void readTest() {
        User user1 = new User("kangwlgns@daum.net", "aaaaa11111");
        User user2 = new User("luckyrkd@naver.com", "aaaaa11111");

        permissionRepository.save(user1);
        permissionRepository.save(user2);

        // 크기가 2이어야 함.
        Assertions.assertThat(permissionRepository.count()).isEqualTo(2);
        // 유저들을 조회.
        Assertions.assertThat(permissionRepository.findAll()).contains(user1).contains(user2);
        // 없는 이메일을 조회해서 가져오면 예외를 반환.
        Assertions.assertThatCode(() -> permissionRepository.findByEmail("kangji0615@gmail.com").get()).isInstanceOf(
            NoSuchElementException.class);
    }

    @Test
    public void UpdateTest() {
        User user = new User("kangwlgns@daum.net", "aaaaa11111");
        String newPassword = "qwer1234";

        User actual = permissionRepository.save(user);
        actual.updatePassword(newPassword);

        // 더티 체킹에 의해 DB에도 업데이트 쿼리가 적용되어야 함.
        Assertions.assertThat(permissionRepository.existsByPassword(newPassword)).isTrue();
    }

    @Test
    public void deleteTest() {
        User user = new User("kangwlgns@daum.net", "aaaaa11111");

        User actual = permissionRepository.save(user);
        permissionRepository.delete(actual);

        // 삽입 후 다시 삭제하면 크기가 0이어야 함.
        Assertions.assertThat(permissionRepository.count()).isEqualTo(0);
    }
}

package gift.Repository;

import gift.DTO.UserDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserDto, Long> {

  UserDto save(UserDto entity);

  Optional<UserDto> findByEmail(String email);

}

package gift.mapper;

import gift.DTO.UserDTO;
import gift.domain.User.UserSimple;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserSimple UserSimple(UserDTO user) {

        return new UserSimple(user.getEmail(), user.getPassword());
    }

    public List<UserSimple> UserSimpleList(List<UserDTO> li) {
        List<UserSimple> list = new ArrayList<>();

        for (UserDTO u : li) {
            list.add(new UserSimple(u.getEmail(), u.getPassword()));
        }
        return list;
    }
}

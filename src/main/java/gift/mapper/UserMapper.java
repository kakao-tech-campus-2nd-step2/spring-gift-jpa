package gift.mapper;

import gift.domain.User.CreateUser;
import gift.domain.User.UpdateUser;
import gift.domain.User.UserSimple;
import gift.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public List<UserSimple> toSimpleList(List<UserEntity> li) {
        List<UserSimple> list = new ArrayList<>();

        for (UserEntity u : li) {
            list.add(new UserSimple(u.getEmail(), u.getPassword()));
        }
        return list;
    }

    public UserEntity toEntity(CreateUser create) {
        return new UserEntity(create.getEmail(), create.getPassword());
    }

    public UserEntity toUpdate(UpdateUser update, UserEntity entity) {
        entity.setPassword(update.getPassword());
        return entity;
    }

    public UserEntity toDelete(UserEntity entity) {
        entity.setIsDelete(1);
        return entity;
    }
}

package gift.controller;

import gift.domain.User;
import gift.domain.User.UserSimple;
import gift.entity.UserEntity;
import gift.mapper.PageMapper;
import gift.util.page.ListResult;
import gift.util.page.PageResult;
import gift.util.page.SingleResult;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public PageResult<UserSimple> getUserList(@Valid User.getList param) {
        return PageMapper.toPageResult(userService.getUserList(param));
    }

    @GetMapping("/{id}")
    public SingleResult<UserEntity> getUser(@PathVariable long id) {
        return new SingleResult<>(userService.getUser(id));
    }

    @PostMapping
    public SingleResult<Long> createUser(@Valid @RequestBody User.CreateUser create) {
        return new SingleResult<>(userService.createUser(create));
    }

    @PutMapping("/{id}")
    public SingleResult<Long> updatePassword(@PathVariable long id,
        @Valid @RequestBody User.UpdateUser update) {
        return new SingleResult<>(userService.updatePassword(id, update));
    }

    @DeleteMapping("/{id}")
    public SingleResult<Long> deleteUser(@PathVariable long id) {
        return new SingleResult<>(userService.deleteUser(id));
    }
}

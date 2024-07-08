package gift.controller;

import gift.domain.User;
import gift.domain.User.UserSimple;
import gift.errorException.ListResult;
import gift.errorException.SingleResult;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ListResult<UserSimple> getUserList() {
        return new ListResult<>(userService.getUserList());
    }

    @GetMapping("/{id}")
    public SingleResult<UserSimple> getUser(@PathVariable long id) {
        return new SingleResult<>(userService.getUser(id));
    }

    @PostMapping
    public SingleResult<Integer> createUser(@Valid @RequestBody User.CreateUser create) {
        return new SingleResult<>(userService.createUser(create));
    }

    @PatchMapping("/{id}")
    public SingleResult<Integer> updatePassword(@PathVariable long id,
        @Valid @RequestBody User.UpdateUser update) {
        return new SingleResult<>(userService.updatePassword(id, update));
    }

    @DeleteMapping("/{id}")
    public SingleResult<Integer> deleteUser(@PathVariable long id) {
        return new SingleResult<>(userService.deleteUser(id));
    }
}

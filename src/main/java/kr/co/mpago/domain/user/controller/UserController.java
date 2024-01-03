package kr.co.mpago.domain.user.controller;

import kr.co.mpago.domain.user.entity.User;
import kr.co.mpago.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{no}")
    public ResponseEntity<User> updateUser(@PathVariable Long no, @RequestBody User user) {
        User updatedUser = userService.updateUser(no, user.getNickname(), user.getPicture());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
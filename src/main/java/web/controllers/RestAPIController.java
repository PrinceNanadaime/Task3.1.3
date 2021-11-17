package web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.exception_handling.NoSuchUserException;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestAPIController {

    private UserService userService;

    public RestAPIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
        User user = userService.show(id);
        if(user == null) {
            throw new NoSuchUserException("There is no user with ID = " +
                    id + " in Database");
        }
        return user;
    }

    @PostMapping("/newUser")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                           @RequestParam("id") long id) {
        userService.update(id,user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
    }
}

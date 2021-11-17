package web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

@Controller
@RequestMapping("/user")
public class UsersController {

    private UserService userService;
    private RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String user (@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("user", user);
        model.addAttribute("allRoles",roleService.getRoles());
        return "user/index";
    }

    @GetMapping("/userNav")
    @ResponseBody
    public ResponseEntity<User> currentUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getUserByName(user.getUsername()), HttpStatus.OK) ;
    }
}

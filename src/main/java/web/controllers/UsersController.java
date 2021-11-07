package web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsersAndRoles (Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", user);
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("user", userService.getUsers());
        model.addAttribute("allRoles", roleService.getRoles());
        return "user/index";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid User user, @RequestParam(value = "checkRoles", required = false) String[] checkRoles) {
        if(checkRoles == null) {
            user.setRoles(Set.of(roleService.getRoleByName("USER")));
        } else {
            for (String role : checkRoles) {
                user.setRoles(Set.of(roleService.getRoleByName(role)));
            }
        }
        userService.update(user);
        return  "redirect:/user";
    }

    @GetMapping("/show/{id}")
    @ResponseBody
    public User show(@PathVariable("id") long id) {
        return userService.show(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/user";
    }
}

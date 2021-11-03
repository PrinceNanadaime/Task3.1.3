package web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String getUsers(Model model) {
        model.addAttribute("user", userService.getUsers());
        return "user/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", user.getRoles());
        return "user/show";
    }

    @PostMapping("/new")
    public String newUser(@ModelAttribute User user, Model model) {
        model.addAttribute("roles", roleService.getRoles());
        return "user/new";
    }


    @PatchMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", roleService.getRoles());
        return "user/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(value = "id") long id) {

        user.setRoles(Set.of(roleService.getRoleById(id)));
        userService.update(user);
        return bindingResult.hasErrors() ? "user/new" : "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/user";

    }
}

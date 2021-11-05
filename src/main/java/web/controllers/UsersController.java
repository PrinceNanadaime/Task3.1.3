package web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.util.Set;

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
    public String index(Model model) {
        model.addAttribute("user", userService.getUsers());
        return "user/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", userService.show(id).getRoles());
        return "user/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute User user, Model model) {
        model.addAttribute("roles", roleService.getRoles());
        return "user/new";
    }

    @PostMapping
    public String create(@RequestParam(value = "ADMIN", required = false) String ADMIN,
                         @RequestParam(value = "USER", required = false) String USER, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "user/new";

        if (ADMIN != null && USER != null) {
            user.setRoles(Set.of(roleService.getRoleByName("USER"), roleService.getRoleByName("ADMIN")));
        } else if (USER != null) {
            user.setRoles(Set.of(roleService.getRoleByName("USER")));
        } else if (ADMIN != null) {
            user.setRoles(Set.of(roleService.getRoleByName("ADMIN")));
        } else user.setRoles(Set.of(roleService.getRoleByName("USER")));
        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        User user = userService.show(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        userService.update(user);
        return "user/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(value = "ADMIN", required = false) String ADMIN,
                         @RequestParam(value = "USER", required = false) String USER) {
        if (bindingResult.hasErrors())
            return "user/new";

        if (ADMIN != null && USER != null) {
            user.setRoles(Set.of(roleService.getRoleByName("USER"), roleService.getRoleByName("ADMIN")));
        } else if (USER != null) {
            user.setRoles(Set.of(roleService.getRoleByName("USER")));
        } else if (ADMIN != null) {
            user.setRoles(Set.of(roleService.getRoleByName("ADMIN")));
        } else user.setRoles(Set.of(roleService.getRoleByName("USER")));
        userService.update(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/user";
    }
}

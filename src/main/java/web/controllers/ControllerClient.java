package web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

@Controller
public class ControllerClient {
    @GetMapping("/client")
    public String client (@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "/client/client";
    }
}

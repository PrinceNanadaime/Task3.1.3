package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Init {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    public void addAdminAndUserToDataBase(){
        roleService.save(new Role("ADMIN"));
        roleService.save(new Role("USER"));

        User user = new User();
        user.setUsername("Marc");
        user.setSurname("Hudson");
        user.setAge(28);
        user.setEmail("marc@mail.ru");
        user.setPassword("marc1987");
        user.setRoles(Set.of(roleService.getRoleByName("USER")));
        userService.save(user);

        User admin = new User();
        admin.setUsername("Nikita");
        admin.setSurname("Kotenkov");
        admin.setAge(19);
        admin.setEmail("nikita@mail.ru");
        admin.setPassword("nikita2001");
        admin.setRoles(Set.of(roleService.getRoleByName("ADMIN"), roleService.getRoleByName("USER")));
        userService.save(admin);
    }
}

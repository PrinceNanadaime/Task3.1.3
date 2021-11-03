package web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import web.dao.RoleDaoImpl;
import web.dao.UserDaoImpl;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.RoleServiceImpl;
import web.service.UserService;
import web.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.Set;

@Configuration
@ComponentScan("web")
public class Init {

    private final UserService userService = new UserServiceImpl(new UserDaoImpl(),new BCryptPasswordEncoder());

    private final RoleService roleService= new RoleServiceImpl(new RoleDaoImpl());

    @PostConstruct
    public void addAdminAndUserToDataBase(){
        roleService.save(new Role("ADMIN"));
        roleService.save(new Role("USER"));

        User user = new User();
        user.setName("Marc");
        user.setSurname("Hudson");
        user.setUsername("marc_hudson");
        user.setPassword("marc1987");
        user.setRoles(Set.of(roleService.getRoleByName("USER")));
        userService.save(user);

        User admin = new User();
        admin.setName("Nikita");
        admin.setSurname("Kotenkov");
        admin.setUsername("nikita_kotenkov");
        admin.setPassword("nikita2001");
        admin.setRoles(Set.of(roleService.getRoleByName("ADMIN"), roleService.getRoleByName("USER")));
        userService.save(admin);
    }
}

package web.service;


import web.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    void save(Role role);

    Role getRoleByName(String name);
}

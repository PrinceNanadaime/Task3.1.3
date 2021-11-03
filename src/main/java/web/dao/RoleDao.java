package web.dao;

import web.models.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getRoles();

    void save(Role role);

    Role getRoleByName(String name);

    Role getRoleByID(long id);
}

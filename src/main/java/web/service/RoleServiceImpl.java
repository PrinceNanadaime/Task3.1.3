package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.models.Role;

import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    public void save(Role role) {
        roleDao.save(role);
    }

    public Role getRoleById(long id) {
        return roleDao.getRoleByID(id);
    }

    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}

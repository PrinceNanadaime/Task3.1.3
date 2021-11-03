package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getRoles() {
        return entityManager.createQuery("select u from Role u", Role.class).getResultList();
    }

    public void save(Role role) {
        entityManager.persist(role);
    }


    public Role getRoleByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select u from Role u where u.name=:role",
                Role.class).setParameter("role", name);
        return query.getSingleResult();
    }

    public Role getRoleByID(long id) {
        TypedQuery<Role> query = entityManager.createQuery("select u from Role u where u.id=:id",
                Role.class).setParameter("id", id);
        return query.getSingleResult();
    }
}

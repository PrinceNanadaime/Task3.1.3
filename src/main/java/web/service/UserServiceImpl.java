package web.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.models.User;

import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userDao.index();
    }

    public User show(long id) {
        return userDao.show(id);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }

    public void delete(long id) {
        userDao.delete(id);
    }

    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }
}

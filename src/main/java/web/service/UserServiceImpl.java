package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.models.User;

import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
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

    public void update(long id,User user) {
        if (!Objects.equals(show(id).getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.update(user);
    }

    public void delete(long id) {
        userDao.delete(id);
    }

    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }
}

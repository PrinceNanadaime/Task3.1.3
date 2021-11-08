package web.service;


import web.models.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User show(long id);

    void save(User user);

    void update(long id,User user);

    void delete(long id);

    User getUserByName(String username);
}

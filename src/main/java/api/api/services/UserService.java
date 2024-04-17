package api.api.services;


import api.api.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();
}

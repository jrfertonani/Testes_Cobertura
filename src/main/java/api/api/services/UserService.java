package api.api.services;


import api.api.domain.User;
import api.api.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO obj);


}

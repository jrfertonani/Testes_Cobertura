package api.api.services;


import api.api.domain.User;

public interface UserService {

    User findById(Integer id);
}

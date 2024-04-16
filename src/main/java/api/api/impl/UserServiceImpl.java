package api.api.impl;

import api.api.domain.User;
import api.api.repositories.UserRepository;
import api.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository Repository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = Repository.findById(id);
        return obj.orElse(null);
    }




}

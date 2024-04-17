package api.api.services.impl;

import api.api.domain.User;
import api.api.repositories.UserRepository;
import api.api.services.UserService;
import api.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " +id));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }


}

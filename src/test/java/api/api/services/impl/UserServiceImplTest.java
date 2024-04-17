package api.api.services.impl;

import api.api.domain.User;
import api.api.dto.UserDTO;
import api.api.repositories.UserRepository;
import api.api.services.exceptions.DataIntegratyViolationException;
import api.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID      = 1;
    public static final String NAME     = "Ademir ";
    public static final String EMAIL    = "emailemail@emailcom";
    public static final String PASSWORD = "123";

    public static final String OBJETO_NAO_ENCONTRADO_ID = "Objeto não encontrado! id: ";
    public static final int INDEX = 0;
    public static final String EMAIL_JA_CADASTRADO_NO_SISTEMA = "Email já cadastrado no sistema!";


    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID,response.getId());
        assertEquals(NAME,response.getName());
        assertEquals(EMAIL,response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnEmptyOptional() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO_ID ));


        try{
            service.findById(ID);
        }catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO_ID,ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1,response.size());
        assertEquals(User.class, response.get(INDEX).getClass());

        assertEquals(ID,response.get(INDEX).getId());
        assertEquals(NAME,response.get(INDEX).getName());
        assertEquals(EMAIL,response.get(INDEX).getEmail());
        assertEquals(PASSWORD,response.get(INDEX).getPassword());


    }

    @Test
    void whenCreateThenReturnAnUserSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID,response.getId());
        assertEquals(NAME,response.getName());
        assertEquals(EMAIL,response.getEmail());
        assertEquals(PASSWORD,response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegriryViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(1);
            service.create(userDTO);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getMessage());
            assertEquals(EMAIL_JA_CADASTRADO_NO_SISTEMA,ex.getMessage());
        }

    }

    @Test
    void whenUpdateThenReturnAnUserSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID,response.getId());
        assertEquals(NAME,response.getName());
        assertEquals(EMAIL,response.getEmail());
        assertEquals(PASSWORD,response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegriryViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(1);
            service.create(userDTO);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getMessage());
            assertEquals(EMAIL_JA_CADASTRADO_NO_SISTEMA,ex.getMessage());
        }

    }


    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO_ID));

        try{
            service.delete(ID);
        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO_ID, ex.getMessage());

        }
    }

    private void startUser(){
        user         = new User                   (ID, NAME, EMAIL, PASSWORD);
        userDTO      = new UserDTO                (ID, NAME, EMAIL, PASSWORD);
        optionalUser =  Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}
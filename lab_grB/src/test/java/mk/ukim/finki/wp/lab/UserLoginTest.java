package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.Role;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import mk.ukim.finki.wp.lab.service.AuthService;
import mk.ukim.finki.wp.lab.service.UserService;
import mk.ukim.finki.wp.lab.service.impl.AuthServiceImpl;
import mk.ukim.finki.wp.lab.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.Assert;


@RunWith(MockitoJUnitRunner.class)
public class UserLoginTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;
    private AuthService authService;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        User user = new User("admin","admin","name","surname", Role.ROLE_ADMIN);
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("admin");

        this.userService = Mockito.spy(new UserServiceImpl(this.userRepository,this.passwordEncoder));
        this.authService = Mockito.spy(new AuthServiceImpl(this.userRepository));
    }

    @Test
    public void testSuccess() throws Exception {
        //this.userService.register("username","password","password","name","surname",Role.ROLE_USER);
        this.userService.register("admin","admin","admin","name1", "surname1",Role.ROLE_USER);
        Mockito.verify(this.userService).register("admin", "admin", "admin", "name1", "surname1", Role.ROLE_USER);
        //User u = this.authService.login("admin",passwordEncoder.encode("admin"));
        //Mockito.verify(this.authService).login("admin","admin");




        //User u = this.userRepository.save(new User("admin", passwordEncoder.encode( "admin"),"name1", "surname1",Role.ROLE_USER));
        //User user = this.authService.login("admin",passwordEncoder.encode("admin"));
        //Mockito.verify(this.authService).login("admin",passwordEncoder.encode("admin"));

        //Assert.assertNotNull("User is null",u);
        //Assert.assertEquals("username not valid", "admin", user.getUsername());
        //Assert.assertEquals("blabl",User.class,userRepository.findByUsernameAndPassword("username", "password"));
    }
}

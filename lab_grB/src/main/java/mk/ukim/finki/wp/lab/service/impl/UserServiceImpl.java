package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Role;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import mk.ukim.finki.wp.lab.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) throws Exception {
        if(username==null || username.isEmpty() || password==null || password.isEmpty())
            throw new Exception("username/pw null/empty");
        if(!password.equals(repeatPassword))
            throw new Exception("not match with repeat pw");
        if(this.userRepository.findByUsername(username).isPresent() ||
                !this.userRepository.findByUsername(username).isEmpty())
            throw new Exception("username already exists");
        User user = new User(username,passwordEncoder.encode(password),name,surname,role);
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }
}

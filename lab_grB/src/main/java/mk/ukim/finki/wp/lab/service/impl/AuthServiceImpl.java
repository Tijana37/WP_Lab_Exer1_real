package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import mk.ukim.finki.wp.lab.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) throws Exception {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new Exception("/login error");
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(Exception::new);

    }
}

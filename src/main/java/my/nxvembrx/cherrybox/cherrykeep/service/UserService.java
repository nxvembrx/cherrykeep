package my.nxvembrx.cherrybox.cherrykeep.service;

import my.nxvembrx.cherrybox.cherrykeep.entity.User;
import my.nxvembrx.cherrybox.cherrykeep.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final Argon2PasswordEncoder argon2PasswordEncoder;

    public UserService(UserRepository userRepository, Argon2PasswordEncoder argon2PasswordEncoder) {
        this.userRepository = userRepository;
        this.argon2PasswordEncoder = argon2PasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("No such user"));
    }

    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(new User());
    }

    public boolean saveUser(User newUser) {
        Optional<User> user = userRepository.findByUsername(newUser.getUsername());

        if (user.isPresent()) {
            return false;
        }

        newUser.setPassword(argon2PasswordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);

        return true;
    }

/*    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }

        return false;
    }*/

}

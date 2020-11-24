package auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import auth.user.UserException.UserNotFoundException;

import static auth.user.UserException.UserAlreadyExistsException;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepo;

    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * Save a new user to the repository.
     */
    public User saveNewUser(User user) {
        try {
            // If a new password is set, encode it.
            String newPassword = user.getPassword();
            if (newPassword != null) {
                user.setPassword(encoder.encode(newPassword));
            }
            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
    }
}


package auth.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserException {
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public static class UserAlreadyExistsException extends RuntimeException {
		private static final long serialVersionUID = 1L;

        public UserAlreadyExistsException(String username) {
            super(String.format("User %s already exists", username));
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class UserNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UserNotFoundException(Long id) {
            super(String.format("User with id: %s not found", id));
        }

        public UserNotFoundException(String username) {
            super(String.format("User with username: %s not found", username));
        }
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public static class UserForbiddenException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

}


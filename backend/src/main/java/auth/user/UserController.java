package auth.user;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import auth.security.JWTService;
import auth.user.UserException.UserNotFoundException;
import auth.user.UserException.UserForbiddenException;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class UserController {
    private final String STATUS = "status";

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    /**
     * Performs SSO and generates JWT
     * @param username
     * @return
     */
    @PostMapping("/sso/authorize/1/{username}")
    @Operation(summary = "Perform SSO and generate JWT")
    public ResponseEntity<Map<String, Object>>  performSSO(@PathVariable String username){
        User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            User user = userService.findByUsername(username);
            if (!authUser.equals(user)){
                // User should only view their own account
                return onUserForbidden();
            }
            if (user.isSuspended()){
                // User should not be able to see their account if suspsended
                return onUserSuspended();
            }

            // User is found
            return onUserFound(user);
        }catch (UserNotFoundException e){
            return  onUserNotFound();
        }
    }

    /**
     * Returns 201 when user is found.
     * @param user
     * @return
     */
    private  ResponseEntity<Map<String, Object>>  onUserFound(User user){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(STATUS, "success");
        body.put("response", jwtService.createJWT(user));
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    /**
     * Returns 404 when user is not found
     * @return
     */
    private  ResponseEntity<Map<String, Object>> onUserNotFound(){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(STATUS, "Not found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Returns 201 when user is suspended
     * @return
     */
    private ResponseEntity<Map<String, Object>>  onUserSuspended(){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(STATUS, "fail");
        body.put("error", "User has been suspended due to inactivity");
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    /**
     * Returns 403 when user does not have correct authorities
     * @return
     */
    private ResponseEntity<Map<String, Object>>  onUserForbidden(){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(STATUS, "forbidden");
        body.put("error", "Access forbidden as this is not your account. Please sign in with the correct credentials.");
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }



}


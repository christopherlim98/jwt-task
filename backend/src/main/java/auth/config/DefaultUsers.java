package auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import auth.user.User;
import auth.user.UserService;

import static auth.user.UserException.UserAlreadyExistsException;

@Component
@Order(1)
public class DefaultUsers implements CommandLineRunner {

    @Autowired
    private UserService userService;

    /**
     * Create default users for jrocket, tcrews, suspended
     *
     * <username:password>
     * jrocket:jrocket
     * tcrews:tcrews
     * suspended:suspended
     */
    @Override
    public void run(String... args) throws Exception {
        addDefaultUser(User.builder().username("jrocket").password("jrocket").authString("ROLE_Manager,ROLE_Project Administrator")
        .givenName("Johnny").surname("Rocket").email("jrocket@ascendaloyalty.com").department("Engineering").build());

        addDefaultUser(User.builder().username("tcrews").password("tcrews").authString("ROLE_Agent,ROLE_Incident Manager")
        .givenName("Terry").surname("Crews").email("tcrews@ascendaloyalty.com").department("Customer Service").build());

        addDefaultUser(User.builder().username("suspended").password("suspended").authString("ROLE_Suspended")
        .givenName("suspended").surname("suspended").email("suspended@ascendaloyalty.com").department("Customer Service").suspended(true).build());
    }

    /**
     * Create default users and add them to user database
     * @param user
     */
    public void addDefaultUser(User user) {
        try {
            System.out.println("[Add user]: " + userService.saveNewUser(user).getUsername());
        } catch (UserAlreadyExistsException e) {
            System.out.println("[Already exists]: " + user.getUsername());
        }
    }
}


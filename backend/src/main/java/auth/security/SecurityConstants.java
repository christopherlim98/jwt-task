package auth.security;

public class SecurityConstants {
    public static final String SECRET = "secret";
    public static final long EXPIRATION_TIME = 864_000_000; //expires after 10 days.
    public static final String BASIC_PREFIX = "Basic ";
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String AUTHORITIES_KEY = "Role";
    public static final String ISSUER = "auth.ascendaloyalty.com";
    public static final String AUDIENCE = "test.ascendaloyalty.com";
    public static final String GIVENNAME_KEY = "GivenName";
    public static final String SURNAME_KEY = "Surname";
    public static final String EMAIL_KEY = "email";
    public static final String DEPT_KEY = "Department";

}


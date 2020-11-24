package auth.user;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;

@Entity
@Getter @Setter @Builder(toBuilder = true)
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;


    @Id
    private String email;

    private String department;

    private String givenName;

    private String surname;

    @Column(unique = true)
    @NotNull(message = "Username cannot be null")
    @Size(min = 5, max = 20, message = "Username should be between 5 and 20 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @JsonProperty("authorities")
    @NotNull(message = "Authorities cannot be null")
    private String authString;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(authString.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private boolean accountNonExpired = true;

    @JsonIgnore
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private boolean accountNonLocked = true;

    @JsonIgnore
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private boolean credentialsNonExpired = true;

    @JsonProperty("active")
    @Builder.Default
    private boolean enabled = true;

    @JsonProperty("suspended")
    @Builder.Default
    private boolean suspended = false;

    public boolean isSuspended(){
        return suspended;
    }
}


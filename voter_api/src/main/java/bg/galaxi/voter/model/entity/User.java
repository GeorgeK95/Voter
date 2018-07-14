package bg.galaxi.voter.model.entity;

import bg.galaxi.voter.model.audit.DateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static bg.galaxi.voter.util.AppConstants.*;


@Entity
@Table(name = USERS, uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                USERNAME
        }),
        @UniqueConstraint(columnNames = {
                EMAIL
        })
})
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = NAME_MAX_VALUE)
    private String name;

    @NotBlank
    @Size(max = USER_NAME_MAX_VALUE)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = EMAIL_MAX_VALUE)
    @Email
    private String email;

    @NotBlank
    @Size(max = PASSWORD_MAX_VALUE)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = USER_ROLES,
            joinColumns = @JoinColumn(name = USER_ID),
            inverseJoinColumns = @JoinColumn(name = ROLE_ID))
    private Set<Role> roles = new HashSet<>();

    private Boolean isBanned;

    public User() {
        this.isBanned = false;
    }

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }
}
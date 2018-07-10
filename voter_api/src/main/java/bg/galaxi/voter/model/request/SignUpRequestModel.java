package bg.galaxi.voter.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static bg.galaxi.voter.util.AppConstants.*;

public class SignUpRequestModel {
    private static final int PASSWORD_MAX_VALUE = 20;
    private static final int PASSWORD_MIN_VALUE = 6;

    @NotBlank
    @Size(min = NAME_MIN_VALUE, max = NAME_MAX_VALUE)
    private String name;

    @NotBlank
    @Size(min = USER_NAME_MIN_VALUE, max = USER_NAME_MAX_VALUE)
    private String username;

    @NotBlank
    @Size(max = EMAIL_MAX_VALUE)
    @Email
    private String email;

    @NotBlank
    @Size(min = PASSWORD_MIN_VALUE, max = PASSWORD_MAX_VALUE)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}

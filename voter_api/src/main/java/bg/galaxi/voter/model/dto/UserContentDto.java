package bg.galaxi.voter.model.dto;

public class UserContentDto {
    private Long id;
    private String username;
    private String name;
    private String role;

    public UserContentDto(Long id, String username, String name, String role) {
        this(id, username, name);
        this.role = role;
    }

    public UserContentDto(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

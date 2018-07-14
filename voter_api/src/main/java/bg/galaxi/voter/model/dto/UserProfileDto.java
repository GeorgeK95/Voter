package bg.galaxi.voter.model.dto;

import java.time.Instant;

public class UserProfileDto {
    private Long id;
    private String username;
    private String name;
    private String role;
    private Boolean isBanned = false;
    private Instant joinedAt;
    private Long pollCount;
    private Long voteCount;

    public UserProfileDto(Long id, String username, String name, Instant joinedAt, Long pollCount,
                          Long voteCount, String roleName, Boolean isBanned) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.pollCount = pollCount;
        this.voteCount = voteCount;
        this.role = roleName;
        this.isBanned = isBanned;
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

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getPollCount() {
        return pollCount;
    }

    public void setPollCount(Long pollCount) {
        this.pollCount = pollCount;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }
}

package bg.galaxi.voter.model.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static bg.galaxi.voter.util.AppConstants.*;

@Entity
@Table(name = TAGS)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = TAG_NAME_LENGH_VALUE, unique = true)
    private String content;

    @ManyToMany(mappedBy = "tags")
    private Set<Poll> polls;

    public Tag() {
    }

    public Tag(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Poll> getPolls() {
        return polls;
    }

    public void setPolls(Set<Poll> polls) {
        this.polls = polls;
    }

    void addPoll(Poll poll) {
        if (this.polls == null) this.polls = new HashSet<>();
        this.polls.add(poll);
    }
}

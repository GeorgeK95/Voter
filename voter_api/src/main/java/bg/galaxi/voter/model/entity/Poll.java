package bg.galaxi.voter.model.entity;

import bg.galaxi.voter.model.audit.UserDateAudit;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bg.galaxi.voter.util.AppConstants.*;

@Entity
@Table(name = POLLS)
public class Poll extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = QUESTION_MAX_VALUE)
    private String question;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = POLL_TAG,
            joinColumns = @JoinColumn(name = POLL_ID),
            inverseJoinColumns = @JoinColumn(name = TAG_ID))
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(
            mappedBy = POLL,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = CHOISES_MIN_VALUE, max = CHOISES_MAX_VALUE)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = CHOISES_BATCH_SIZE)
    private List<Choice> choices = new ArrayList<>();

    @NotNull
    private Instant expirationDateTime;

    private Boolean isDeleted = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setPoll(this);
    }

    public void removeChoice(Choice choice) {
        choices.remove(choice);
        choice.setPoll(null);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.addPoll(this);
    }
}

package bg.galaxi.voter.model.response;

import bg.galaxi.voter.model.dto.UserContentDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class PollResponseModel {
    private Long id;
    private String question;
    private List<TagResponseModel> tags;
    private List<ChoiceResponseModel> choices;
    private UserContentDto createdBy;
    private Instant creationDateTime;
    private Instant expirationDateTime;
    private Boolean isExpired;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long selectedChoice;
    private Long totalVotes;

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

    public List<ChoiceResponseModel> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceResponseModel> choices) {
        this.choices = choices;
    }

    public UserContentDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserContentDto createdBy) {
        this.createdBy = createdBy;
    }


    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Long getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(Long selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    public Long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<TagResponseModel> getTags() {
        return tags;
    }

    public void setTags(List<TagResponseModel> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollResponseModel that = (PollResponseModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(question, that.question) &&
                Objects.equals(choices, that.choices) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(creationDateTime, that.creationDateTime) &&
                Objects.equals(expirationDateTime, that.expirationDateTime) &&
                Objects.equals(isExpired, that.isExpired) &&
                Objects.equals(selectedChoice, that.selectedChoice) &&
                Objects.equals(totalVotes, that.totalVotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, choices, createdBy, creationDateTime, expirationDateTime, isExpired, selectedChoice, totalVotes);
    }
}

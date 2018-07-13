package bg.galaxi.voter.model.request;

import bg.galaxi.voter.model.dto.PollLength;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static bg.galaxi.voter.util.AppConstants.*;

public class PollRequestModel {
    @NotBlank
    @Size(max = QUESTION_MAX_VALUE)
    private String question;

    @Nullable
    private List<ChoiceRequestModel> tags;

    @NotNull
    @Size(min = CHOISES_MIN_VALUE, max = CHOISES_MAX_VALUE)
    @Valid
    private List<ChoiceRequestModel> choices;

    @NotNull
    @Valid
    private PollLength pollLength;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceRequestModel> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceRequestModel> choices) {
        this.choices = choices;
    }

    public PollLength getPollLength() {
        return pollLength;
    }

    public void setPollLength(PollLength pollLength) {
        this.pollLength = pollLength;
    }

    public List<ChoiceRequestModel> getTags() {
        return tags;
    }

    public void setTags(List<ChoiceRequestModel> tags) {
        this.tags = tags;
    }
}

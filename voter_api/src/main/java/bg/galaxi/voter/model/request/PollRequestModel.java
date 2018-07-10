package bg.galaxi.voter.model.request;

import bg.galaxi.voter.model.dto.PollLengthDto;

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

    @NotNull
    @Size(min = CHOISES_MIN_VALUE, max = CHOISES_MAX_VALUE)
    @Valid
    private List<ChoiceRequestModel> choices;

    @NotNull
    @Valid
    private PollLengthDto pollLengthDto;

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

    public PollLengthDto getPollLengthDto() {
        return pollLengthDto;
    }

    public void setPollLengthDto(PollLengthDto pollLengthDto) {
        this.pollLengthDto = pollLengthDto;
    }
}

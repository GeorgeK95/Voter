package bg.galaxi.voter.model.request;
import javax.validation.constraints.NotNull;

public class VoteRequestModel {
    @NotNull
    private Long choiceId;

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }
}


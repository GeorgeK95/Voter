package bg.galaxi.voter.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static bg.galaxi.voter.util.AppConstants.TEXT_MAX_VALUE;

public class TagRequestModel {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

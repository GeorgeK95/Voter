package bg.galaxi.voter.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class PollLengthDto {
    @NotNull
    @Max(7)
    private Integer days;

    @NotNull
    @Max(23)
    private Integer hours;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}

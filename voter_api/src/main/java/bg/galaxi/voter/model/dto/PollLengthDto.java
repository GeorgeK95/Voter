package bg.galaxi.voter.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import static bg.galaxi.voter.util.AppConstants.DAYS_MAX_VALUE;
import static bg.galaxi.voter.util.AppConstants.HOURS_MAX_VALUE;

public class PollLengthDto {

    @NotNull
    @Max(DAYS_MAX_VALUE)
    private Integer days;

    @NotNull
    @Max(HOURS_MAX_VALUE)
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

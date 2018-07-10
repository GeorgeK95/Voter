package bg.galaxi.voter.model.dto;

public class UserAvailabilityDto {
    private Boolean available;

    public UserAvailabilityDto(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}

package bg.galaxi.voter.util;

import bg.galaxi.voter.model.entity.Poll;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.model.response.ChoiceResponseModel;
import bg.galaxi.voter.model.response.PollResponseModel;
import bg.galaxi.voter.model.dto.UserContentDto;

import java.time.Instant;
import java.util.stream.Collectors;

public class CustomModelMapper {

    public static PollResponseModel mapEntityToResponseModel(Poll poll, User creator, Long userVote) {
        PollResponseModel pollResponseModel = DTOConverter.convert(poll, PollResponseModel.class);
        pollResponseModel.setTags(
                pollResponseModel.getTags().stream().sorted().collect(Collectors.toList())
        );
        pollResponseModel.setCreationDateTime(poll.getCreatedAt());
        pollResponseModel.setExpirationDateTime(poll.getExpirationDateTime());
        pollResponseModel.setExpired(poll.getExpirationDateTime().isBefore(Instant.now()));
        pollResponseModel.setChoices(DTOConverter.convert(poll.getChoices(), ChoiceResponseModel.class));
        pollResponseModel.setCreatedBy(new UserContentDto(creator.getId(), creator.getUsername(), creator.getName()));
        if (userVote != null) pollResponseModel.setSelectedChoice(userVote);
        pollResponseModel.setTotalVotes(pollResponseModel.getChoices().stream().mapToLong(ChoiceResponseModel::getVoteCount).sum());

        return pollResponseModel;
    }

}

package bg.galaxi.voter.util;

import bg.galaxi.voter.model.entity.Poll;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.model.response.ChoiceResponseModel;
import bg.galaxi.voter.model.response.PollResponseModel;
import bg.galaxi.voter.model.dto.UserContentDto;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static PollResponseModel mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap, User creator, Long userVote) {
        PollResponseModel pollResponseModel = new PollResponseModel();
        pollResponseModel.setId(poll.getId());
        pollResponseModel.setQuestion(poll.getQuestion());
        pollResponseModel.setCreationDateTime(poll.getCreatedAt());
        pollResponseModel.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollResponseModel.setExpired(poll.getExpirationDateTime().isBefore(now));

        List<ChoiceResponseModel> choiceResponsModels = poll.getChoices().stream().map(choice -> {
            ChoiceResponseModel choiceResponseModel = new ChoiceResponseModel();
            choiceResponseModel.setId(choice.getId());
            choiceResponseModel.setText(choice.getText());

            if(choiceVotesMap.containsKey(choice.getId())) {
                choiceResponseModel.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                choiceResponseModel.setVoteCount(0);
            }
            return choiceResponseModel;
        }).collect(Collectors.toList());

        pollResponseModel.setChoices(choiceResponsModels);
        UserContentDto creatorSummary = new UserContentDto(creator.getId(), creator.getUsername(), creator.getName());
        pollResponseModel.setCreatedBy(creatorSummary);

        if(userVote != null) {
            pollResponseModel.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponseModel.getChoices().stream().mapToLong(ChoiceResponseModel::getVoteCount).sum();
        pollResponseModel.setTotalVotes(totalVotes);

        return pollResponseModel;
    }

}

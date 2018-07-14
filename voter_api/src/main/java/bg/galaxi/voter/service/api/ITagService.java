package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.entity.Poll;
import bg.galaxi.voter.model.entity.Tag;
import bg.galaxi.voter.model.request.TagRequestModel;
import bg.galaxi.voter.model.response.TagResponseModel;

import java.util.List;
import java.util.Set;

public interface ITagService {
    List<TagResponseModel> getTagsByName(String tagRequestModel);

    Tag findByName(String text);

    List<Poll> findPollsByTagName(String current);
}

package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.request.TagRequestModel;
import bg.galaxi.voter.model.response.TagResponseModel;

import java.util.List;
import java.util.Set;

public interface ITagService {
    List<TagResponseModel> getTagsByName(String tagRequestModel);

    Set<String> getAllTagsAsStrings();
}

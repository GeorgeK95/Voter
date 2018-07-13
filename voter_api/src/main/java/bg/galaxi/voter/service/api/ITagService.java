package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.request.TagRequestModel;
import bg.galaxi.voter.model.response.TagResponseModel;

import java.util.List;

public interface ITagService {
    List<TagResponseModel> getTagsByName(String tagRequestModel);
}

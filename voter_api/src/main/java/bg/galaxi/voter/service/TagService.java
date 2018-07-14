package bg.galaxi.voter.service;

import bg.galaxi.voter.model.entity.Tag;
import bg.galaxi.voter.model.request.TagRequestModel;
import bg.galaxi.voter.model.response.TagResponseModel;
import bg.galaxi.voter.repository.TagRepository;
import bg.galaxi.voter.service.api.ITagService;
import bg.galaxi.voter.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagResponseModel> getTagsByName(String tag) {
        List<Tag> found = this.tagRepository.getTagsByName(tag);

        if (found == null) return null;

        return DTOConverter.convert(found, TagResponseModel.class);
    }

    @Override
    public Set<String> getAllTagsAsStrings() {
        return this.tagRepository.getAllTagsAsStrings();
    }

    @Override
    public Tag findByName(String text) {
        return this.tagRepository.findByContent(text);
    }
}

package bg.galaxi.voter.controller;

import bg.galaxi.voter.model.response.TagResponseModel;
import bg.galaxi.voter.service.api.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bg.galaxi.voter.util.AppConstants.*;

@RestController
@RequestMapping(API_TAGS_URL)
public class TagController {

    private final ITagService tagService;

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(TAG_NAME_URL)
    @PreAuthorize(HAS_ROLE_USER)
    public List<TagResponseModel> getTagsByName(@PathVariable String content) {
        return this.tagService.getTagsByName(content);
    }

}

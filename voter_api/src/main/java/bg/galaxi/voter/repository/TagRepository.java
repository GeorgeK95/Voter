package bg.galaxi.voter.repository;

import bg.galaxi.voter.model.entity.Poll;
import bg.galaxi.voter.model.entity.Tag;
import bg.galaxi.voter.model.request.TagRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t where t.content like :param%")
    List<Tag> getTagsByName(@Param("param") String param);

    Tag findByContent(String text);

    @Query("select t.polls from Tag t where t.content like :current")
    List<Poll> getTagPolls(@Param("current") String current);
}

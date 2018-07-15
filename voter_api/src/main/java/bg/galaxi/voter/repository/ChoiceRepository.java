package bg.galaxi.voter.repository;

import bg.galaxi.voter.model.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}

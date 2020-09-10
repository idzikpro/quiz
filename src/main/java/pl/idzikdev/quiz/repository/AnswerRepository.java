package pl.idzikdev.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.idzikdev.quiz.domain.entity.AnswerEntity;

@Repository
public interface AnswerRepository extends CrudRepository<AnswerEntity,Long> {
}

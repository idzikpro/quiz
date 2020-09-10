package pl.idzikdev.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.idzikdev.quiz.domain.entity.QuestionEntity;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity,Long> {

    @Query("select items from QuestionEntity items where items.number > :number")
    public List<QuestionEntity> getQuestionsWithNumberGraterThen(@Param("number") Long number);

    public List<QuestionEntity> findByNumberGreaterThanEqualOrderByNumberAsc(Long number);

    @Query("select items from QuestionEntity items where items.name like :loser")
    public List<QuestionEntity> getQuestionsByNameLike(@Param("loser") String like);

    @Override
    Page<QuestionEntity> findAll(Pageable pageable);
}

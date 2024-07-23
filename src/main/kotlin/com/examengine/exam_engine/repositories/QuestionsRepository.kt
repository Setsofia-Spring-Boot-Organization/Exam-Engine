package com.examengine.exam_engine.repositories

import com.examengine.exam_engine.entities.QuestionsEntity
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface QuestionsRepository : CrudRepository<QuestionsEntity, String> {
    @Query("{ 'receivers': ?0 }")
    fun findQuestionsEntitiesByReceiversEmail(studentEmail: String): List<QuestionsEntity>
    fun findQuestionsEntitiesByCreator(creatorId: String?) : List<QuestionsEntity>

    /**
     * This function get the total count of students that are responsible for answering a particular question.
     * @param questionId id that is used to locate of fetch the question
     * @param creatorId the id of the person that created the question; thus the teacher
     * @return Optional<QuestionEntity>
     */
    fun findQuestionsEntityByQuestionIdAndCreator(questionId: String, creatorId: String?): Optional<QuestionsEntity>
}
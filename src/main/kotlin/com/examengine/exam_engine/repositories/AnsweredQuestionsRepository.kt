package com.examengine.exam_engine.repositories

import com.examengine.exam_engine.entities.AnsweredQuestionsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AnsweredQuestionsRepository : CrudRepository<AnsweredQuestionsEntity, String>{
    fun findByQuestionIdAndUserId(questionId: String, userId: String): Optional<AnsweredQuestionsEntity>
}
package com.examengine.exam_engine.repositories

import com.examengine.exam_engine.entities.QuestionsEntity
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionsRepository : CrudRepository<QuestionsEntity, String> {
    @Query("{ 'receivers.email': ?0 }")
    fun findQuestionsEntitiesByReceiversEmail(studentEmail: String): List<QuestionsEntity>
    fun findQuestionsEntitiesByCreator(creatorId: String?) : List<QuestionsEntity>
}
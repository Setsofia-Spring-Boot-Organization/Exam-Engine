package com.examengine.exam_engine.utilities

import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.entities.AnsweredQuestionsEntity
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.repositories.AnsweredQuestionsRepository
import com.examengine.exam_engine.repositories.QuestionsRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class TeacherQuestionUtil(
    private var questionsRepository: QuestionsRepository,
    private val answeredQuestionsRepository: AnsweredQuestionsRepository
) {
    /**
     * @param questionId the question id as a parameter is used to fetch a single question from the database
     * @param teacherId the id of the teacher that is requesting the question
     * @return QuestionEntity
     */
    fun getSingleQuestion(questionId: String, teacherId: String): QuestionsEntity {
        val question: Optional<QuestionsEntity> = questionsRepository.findQuestionsEntityByQuestionIdAndCreator(questionId, teacherId)

        if (question.isEmpty) {
            throw MyExceptions(Reasons.NO_QUESTIONS_FOUND)
        } else {
            return question.get()
        }
    }

    /**
     * @param totalCount the total count of students
     * @return ResponseEntity<QuestionsDAO>
     */
    fun showTotalCountResponse(totalCount: Int): ResponseEntity<QuestionsDAO> {
        return ResponseEntity.ok(QuestionsDAO
            .Builder()
            .status(200)
            .message("success")
            .totalCounts(totalCount)
            .build()
        )
    }

    /**
     * @param questionId the question id as a parameter is used to fetch question from the database
     * @param teacherId the id of the teacher that is requesting the question
     * @return QuestionEntity
     */
    fun getSubmittedAnswers(questionId: String, teacherId: String): List<AnsweredQuestionsEntity> {
        getSingleQuestion(questionId, teacherId) // verify that the question exists
        val answeredQuestionsEntity: List<AnsweredQuestionsEntity> = answeredQuestionsRepository.findAnsweredQuestionsEntitiesByQuestionId(questionId)

        return answeredQuestionsEntity
    }
}
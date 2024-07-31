package com.examengine.exam_engine.utilities

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dao.StudentQuestionsDAO
import com.examengine.exam_engine.entities.AnsweredQuestionsEntity
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.enums.QuestionStatus
import com.examengine.exam_engine.repositories.AnsweredQuestionsRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class StudentQuestionUtil(
    private val answeredQuestionsRepository: AnsweredQuestionsRepository
) {
    fun iteratedStudentQuestions(question: QuestionsEntity, studentId: String): QuestionsDAO {
        val answeredQuestionsEntity: Optional<AnsweredQuestionsEntity> = answeredQuestionsRepository.findByQuestionIdAndUserId(question.questionId!!, studentId)
        return if (answeredQuestionsEntity.isPresent) {
            responseDAO(question, QuestionStatus.DONE)
        } else if (!answeredQuestionsEntity.isPresent && question.questionEndTime < LocalDateTime.now()) {
            responseDAO(question, QuestionStatus.EXPIRED)
        } else {
            responseDAO(question, QuestionStatus.PENDING)
        }
    }

    private fun responseDAO(question: QuestionsEntity, questionStatus: QuestionStatus): QuestionsDAO {
        return QuestionsDAO.Builder()
            .questionId(question.questionId!!)
            .questionStatus(questionStatus)
            .dateCreated(question.dateCreated)
            .questionTitle(question.questionTitle)
            .questionInstructions(question.questionInstruction)
            .questionStartTime(question.questionStartTime)
            .questionEndTime(question.questionEndTime)
            .studentQuestions(question.question.map { studentQuestion ->
                StudentQuestionsDAO
                    .Builder()
                    .id(studentQuestion.id)
                    .text(studentQuestion.text)
                    .type(studentQuestion.type)
                    .score(studentQuestion.score)
                    .options(studentQuestion.options)
                    .build()

            })
            .build()
    }

    fun successQuestionRequestResponse(questionsDAO: List<QuestionsDAO>): AllQuestionsDAO {
        val questions = ArrayList<QuestionsDAO>()
        for (question in questionsDAO) {

            questions.add(
                QuestionsDAO
                    .Builder()
                    .questionId(question.questionId)
                    .dateCreated(question.dateCreated!!)
                    .questionTitle(question.questionTitle)
                    .questionInstructions(question.questionInstructions)
                    .questionStatus(question.questionStatus!!)
                    .questionStartTime(question.questionStartTime!!)
                    .questionEndTime(question.questionEndTime!!)
                    .studentQuestions(question.studentQuestions!!)
                    .receivers(question.receivers)
                    .build()
            )
        }

        return AllQuestionsDAO(
            status = 200,
            message = "success",
            questions = questions
        )
    }
}
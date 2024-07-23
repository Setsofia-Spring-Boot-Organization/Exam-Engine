package com.examengine.exam_engine.utilities

import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dao.StudentQuestionsDAO
import com.examengine.exam_engine.entities.AnsweredQuestionsEntity
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.enums.QuestionStatus
import com.examengine.exam_engine.repositories.AnsweredQuestionsRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class StudentQuestionUtil(
    private val answeredQuestionsRepository: AnsweredQuestionsRepository
) {
    fun iteratedStudentQuestions(question: QuestionsEntity, studentId: String): QuestionsDAO {
        val answeredQuestionsEntity: Optional<AnsweredQuestionsEntity> = answeredQuestionsRepository.findByQuestionIdAndUserId(question.questionId!!, studentId)
        if (answeredQuestionsEntity.isPresent) {
            return QuestionsDAO.Builder()
                .questionId(question.questionId!!)
                .questionStatus(QuestionStatus.DONE)
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
        } else {
            return QuestionsDAO.Builder()
                .questionId(question.questionId!!)
                .questionStatus(QuestionStatus.PENDING)
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
    }
}
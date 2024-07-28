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

    fun successQuestionRequestResponse(questionsDAO: List<QuestionsDAO>): AllQuestionsDAO {
        val questions = ArrayList<QuestionsDAO>()
        for (question in questionsDAO) {

            val questionStatus =  if (question.questionEndTime!! < LocalDateTime.now()) {
                QuestionStatus.DONE
            } else {
                QuestionStatus.ACTIVE
            }

            questions.add(
                QuestionsDAO
                    .Builder()
                    .questionId(question.questionId)
                    .dateCreated(question.dateCreated!!)
                    .questionTitle(question.questionTitle)
                    .questionStatus(questionStatus)
                    .questionStartTime(question.questionStartTime!!)
                    .questionEndTime(question.questionEndTime!!)
                    .studentQuestions(question.studentQuestions!!)
                    .receivers(question.receivers)
                    .build()
            )
        }

        println(questions)

        return AllQuestionsDAO(
            status = 200,
            message = "success",
            questions = questions
        )
    }
}
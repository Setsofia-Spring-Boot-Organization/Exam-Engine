package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.*
import com.examengine.exam_engine.dto.*
import com.examengine.exam_engine.entities.AnsweredQuestionsEntity
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.entities.StudentAnswersEntity
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.interfaces.QuestionsInterface
import com.examengine.exam_engine.repositories.AnsweredQuestionsRepository
import com.examengine.exam_engine.repositories.QuestionsRepository
import com.examengine.exam_engine.repositories.StudentAnswersRepository
import com.examengine.exam_engine.utilities.QuestionUtil
import com.examengine.exam_engine.utilities.StudentQuestionUtil
import com.examengine.exam_engine.utilities.StudentUtil
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional

@Service
@RequiredArgsConstructor
class QuestionServiceImpl(
    private var studentUtil: StudentUtil,
    private var questionUtil: QuestionUtil,
    private var studentQuestionUtil: StudentQuestionUtil,
    private var questionsRepository: QuestionsRepository,
    private val studentAnswersRepository: StudentAnswersRepository,
    private val answeredQuestionsRepository: AnsweredQuestionsRepository
) : QuestionsInterface {
    override fun getAllStudentQuestions(studentId: String): ResponseEntity<AllQuestionsDAO> {

        val user = studentUtil.getStudent(studentId)

        val questions: List<QuestionsEntity> = questionsRepository.findQuestionsEntitiesByReceiversEmail(user.userEmail)
        if (questions.isEmpty()) throw MyExceptions(Reasons.NO_QUESTIONS_FOUND)

        val questionsDAO = ArrayList<QuestionsDAO>()
        for (question in questions) {
            val questionDAO = studentQuestionUtil.iteratedStudentQuestions(question, studentId)
            questionsDAO.add(questionDAO)
        }

        return ResponseEntity.status(200).body(questionUtil.successQuestionRequestResponse(questionsDAO))
    }


    override fun studentAnswerQuestion(
        studentId: String,
        studentAnswersDTO: StudentAnswersDTO
    ): ResponseEntity<AnsweredQuestionsDAO> {

        val student = studentUtil.getStudent(studentId)

        // Check if the question exists
        val isQuestion = questionsRepository.findById(studentAnswersDTO.questionId)
        if (!isQuestion.isPresent) throw MyExceptions(Reasons.NO_QUESTIONS_FOUND)

        val question = isQuestion.get()
        val answersDAO = ArrayList<AnswersDAO>()
        val totalMark = ArrayList<Int>()

        val isQuestionAnswered = answeredQuestionsRepository.findByQuestionIdAndUserId(question.questionId!!, studentId)
        if (isQuestionAnswered.isPresent) throw MyExceptions(Reasons.QUESTION_ALREADY_ANSWERED)

        if (question.questionEndTime < LocalDateTime.now()) throw MyExceptions(Reasons.QUESTION_EXPIRED)

        // Iterate over student answers
        for (studentAnswer in studentAnswersDTO.studentAnswers) {
//             Find the corresponding teacher question
            val teacherQuestion = question.question.find { it.id == studentAnswer.id }
            if (teacherQuestion != null) {
                // Check if the student's email is one of the receivers' emails
                val isReceiver = question.receivers.any { it.contains(student.userEmail) }
                if (isReceiver) {
                    // Iterate over each possible answer for the current question
                    for (teacherAnswer in teacherQuestion.correctAnswers) {
                        val isCorrect = teacherAnswer == studentAnswer.answer
                        val result = if (isCorrect) {
                            totalMark.add(teacherQuestion.score)
                            "Correct"
                        } else "Incorrect"
                        println("$teacherAnswer - $result")

                        // TODO: ADD THE ANSWERS HERE
                        val answerDAO = AnswersDAO.Builder()
                            .id(teacherQuestion.id)
                            .text(teacherQuestion.text)
                            .type(teacherQuestion.type.name)
                            .options(teacherQuestion.options)
                            .userChoice(listOf(studentAnswer.answer!!))
                            .correctAnswers(teacherQuestion.correctAnswers)
                            .build()
                        // add the question and answer to the dao
                        answersDAO.add(answerDAO)
                    }
                }
            }
        }

        println("THE STUDENTS TOTAL SCORE: ${totalMark.sum()}")
        val remark = if (totalMark.sum() == question.passMark || totalMark.sum() > question.passMark) {
            "pass"
        } else {
            "fail"
        }

        if (answersDAO.isEmpty()) throw MyExceptions(Reasons.ANSWERS_NOT_SUBMITTED)
        val studentAnswers = StudentAnswersEntity(
            questionId = question.questionId!!,
            studentId = studentId,
            answers = answersDAO,
            totalMarks = totalMark.sum()
        )
        studentAnswersRepository.save(studentAnswers)

        // update the answered question status to DONE
        saveAnsweredQuestionStatus(question.questionId!!, student.id!!, remark)

        return ResponseEntity.ok(AnsweredQuestionsDAO.Builder()
            .status(200)
            .message("success")
            .questionId(question.questionId!!)
            .answers(answersDAO.map { answer ->
                AnswersDAO
                    .Builder()
                    .id(answer.id)
                    .text(answer.text)
                    .type(answer.type)
                    .options(answer.options)
                    .build()
            })
            .totalScore(totalMark.sum())
            .remarks(remark)
            .build())
    }


    override fun getAllStudentAnswerHistory(studentId: String): ResponseEntity<AnswerHistoryDAO> {

        val student = studentUtil.getStudent(studentId)
        val answerHistory = studentAnswersRepository.findStudentAnswersEntitiesByStudentId(student.id!!)

        if (answerHistory.isEmpty()) throw MyExceptions(Reasons.NO_ANSWERS_AVAILABLE)

        return ResponseEntity.ok(
            AnswerHistoryDAO.Builder()
                .status(200)
                .message("success")
                .answers(answerHistory)
                .build()
        )
    }

    // some helper functions
    private fun saveAnsweredQuestionStatus(questionId: String, userId: String, remark: String) {
        // find the answered question and update its status from ACTIVE to DONE
        val isAnsweredQuestion: Optional<AnsweredQuestionsEntity> = answeredQuestionsRepository.findByQuestionIdAndUserId(questionId, userId)

        if (isAnsweredQuestion.isPresent) {
            return
        } else {
            val newAnsweredQuestionsEntity = AnsweredQuestionsEntity(
                questionId = questionId,
                userId = userId,
                remark = remark
            )
            answeredQuestionsRepository.save(newAnsweredQuestionsEntity)
        }
    }
}
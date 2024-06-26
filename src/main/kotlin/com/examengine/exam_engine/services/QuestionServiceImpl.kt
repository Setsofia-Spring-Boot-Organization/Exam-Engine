package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.AnsweredQuestionsDAO
import com.examengine.exam_engine.dao.AnswersDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dto.*
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.entities.StudentAnswersEntity
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.interfaces.QuestionsInterface
import com.examengine.exam_engine.repositories.QuestionsRepository
import com.examengine.exam_engine.repositories.StudentAnswersRepository
import com.examengine.exam_engine.utilities.QuestionUtil
import com.examengine.exam_engine.utilities.StudentUtil
import com.examengine.exam_engine.utilities.TeacherUtil
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class QuestionServiceImpl(
    private var teacherUtil: TeacherUtil,
    private var studentUtil: StudentUtil,
    private var questionUtil: QuestionUtil,
    private var questionsRepository: QuestionsRepository,
    private val studentAnswersRepository: StudentAnswersRepository
) : QuestionsInterface {

    override fun createNewQuestions(teacherId: String, questionsDTO: QuestionDetailsDTO): ResponseEntity<QuestionsDAO> {
        val user = teacherUtil.getTeacher(teacherId)
        val newQuestion = user.id?.let { questionUtil.createQuestion(it, questionsDTO) }

        val createdQuestion = questionsRepository.save(newQuestion!!)
        return ResponseEntity.status(200).body(questionUtil.newCreatedQuestionResponse(createdQuestion))
    }


    override fun getAllTeacherQuestions(teacherId: String): ResponseEntity<AllQuestionsDAO> {
        val user = teacherUtil.getTeacher(teacherId)

        val questions: List<QuestionsEntity> = questionsRepository.findQuestionsEntitiesByCreator(user.id)
        if (questions.isEmpty()) throw MyExceptions(Reasons.NO_QUESTIONS_FOUND)

        val questionsDAO = ArrayList<QuestionsDAO>()
        for (question in questions) {
            val questionDAO = questionUtil.iteratedQuestions(question)
            questionsDAO.add(questionDAO)
        }

        return ResponseEntity.status(200).body(questionUtil.successQuestionRequestResponse(questionsDAO))
    }


    override fun getAllStudentQuestions(studentId: String): ResponseEntity<AllQuestionsDAO> {
        val user = studentUtil.getStudent(studentId)

        val questions: List<QuestionsEntity> = questionsRepository.findQuestionsEntitiesByReceiversEmail(user.userEmail)
        if (questions.isEmpty()) throw MyExceptions(Reasons.NO_QUESTIONS_FOUND)

        val questionsDAO = ArrayList<QuestionsDAO>()
        for (question in questions) {
            val questionDAO = questionUtil.iteratedQuestions(question)
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

        // Iterate over student answers
        for (studentAnswer in studentAnswersDTO.studentAnswers) {
            // Find the corresponding teacher question
            val teacherQuestion = question.question.find { it.id == studentAnswer.id }
            if (teacherQuestion != null) {
                // Check if the student's email is one of the receivers' emails
                val isReceiver = question.receivers.any { it.contains(student.userEmail) }
                if (isReceiver) {
                    println("\nQuestion ${teacherQuestion.id}:")
                    println("Question: ${teacherQuestion.text}")
                    println("Question type: ${teacherQuestion.type}")
                    teacherQuestion.options.listIterator().forEach { println(it) }

                    // Iterate over each possible answer for the current question
                    for (teacherAnswer in teacherQuestion.correctAnswers) {
                        val isCorrect = teacherAnswer == studentAnswer.answer
                        val result = if (isCorrect) "Correct" else "Incorrect"
                        println("$teacherAnswer - $result")

                        // TODO: ADD THE ANSWERS HERE
                        val answerDAO = AnswersDAO.Builder()
                            .id(teacherQuestion.id)
                            .text(teacherQuestion.text)
                            .type(teacherQuestion.type.name)
                            .options(teacherQuestion.options)
                            .correctAnswers(teacherQuestion.correctAnswers)
                            .build()
                        // add the question and answer to the dao
                        answersDAO.add(answerDAO)
                    }
                }
            }
        }

        if (answersDAO.isEmpty()) throw MyExceptions(Reasons.ANSWERS_NOT_SUBMITTED)
        val studentAnswers = StudentAnswersEntity(
            questionId = question.questionId!!,
            studentId = studentId,
            answers = answersDAO
        )
        studentAnswersRepository.save(studentAnswers)

        return ResponseEntity.ok(AnsweredQuestionsDAO.Builder()
            .status(200)
            .message("success")
            .questionId(question.questionId!!)
            .answers(answersDAO)
            .build())
    }
}
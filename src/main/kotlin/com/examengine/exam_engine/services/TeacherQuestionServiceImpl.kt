package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.entities.Users
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.interfaces.TeacherQuestionsInterface
import com.examengine.exam_engine.repositories.QuestionsRepository
import com.examengine.exam_engine.utilities.QuestionUtil
import com.examengine.exam_engine.utilities.StudentUtil
import com.examengine.exam_engine.utilities.TeacherQuestionUtil
import com.examengine.exam_engine.utilities.TeacherUtil
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TeacherQuestionServiceImpl(
    private var teacherUtil: TeacherUtil,
    private var questionUtil: QuestionUtil,
    private var questionsRepository: QuestionsRepository,
    private val teacherQuestionUtil: TeacherQuestionUtil,
    private val studentUtil: StudentUtil
): TeacherQuestionsInterface {

    // TODO: add total marks to the response
    override fun createNewQuestions(teacherId: String, questionsDTO: QuestionDetailsDTO): ResponseEntity<QuestionsDAO> {
        val user = teacherUtil.getTeacher(teacherId)
        val newQuestion = user.id?.let { questionUtil.createQuestion(it, questionsDTO) }

        try {
            val createdQuestion = questionsRepository.save(newQuestion!!)
            return ResponseEntity.status(200).body(questionUtil.newCreatedQuestionResponse(createdQuestion))
        } catch (exception: Exception) {
            throw MyExceptions(Reasons.ERROR_CREATING_QUESTION)
        }
    }

    override fun getAllTeacherQuestionsWithLimitedResults(teacherId: String): ResponseEntity<AllQuestionsDAO> {
        val user = teacherUtil.getTeacher(teacherId)

        val questions: List<QuestionsEntity> = questionsRepository.findQuestionsEntitiesByCreator(user.id)
        if (questions.isEmpty()) throw MyExceptions(Reasons.NO_QUESTIONS_FOUND)

        val questionsDAO = ArrayList<QuestionsDAO>()
        for (question in questions) {
            val questionDAO = questionUtil.iteratedQuestions(question)
            questionsDAO.add(questionDAO)
        }

        return ResponseEntity.status(200).body(questionUtil.getAllTeacherQuestionsWithLimitedResults(questionsDAO))
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

    override fun getAllTeacherQuestionsReceiversCount(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO> {
        val question: QuestionsEntity = teacherQuestionUtil.getSingleQuestion(questionId, teacherId)

        return teacherQuestionUtil.showTotalCountResponse(question)
    }

    override fun getAllTotalCountOfDoneStudents(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO> {
        return teacherQuestionUtil.getSubmittedAnswers(questionId, teacherId)
    }

    override fun getAllTotalCountOfPassStudents(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO> {
        return teacherQuestionUtil.getPassOrFailedStudents(questionId, teacherId, "pass")
    }

    override fun getAllTotalCountOfFailedStudents(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO> {
        return teacherQuestionUtil.getPassOrFailedStudents(questionId, teacherId, "fail")
    }
}
package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.interfaces.QuestionsInterface
import com.examengine.exam_engine.repositories.QuestionsRepository
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
    private var questionsRepository: QuestionsRepository
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
}
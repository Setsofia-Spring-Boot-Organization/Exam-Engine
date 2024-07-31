package com.examengine.exam_engine.utilities

import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dao.UserDAO
import com.examengine.exam_engine.entities.AnsweredQuestionsEntity
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.repositories.AnsweredQuestionsRepository
import com.examengine.exam_engine.repositories.QuestionsRepository
import com.examengine.exam_engine.repositories.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@Component
class TeacherQuestionUtil(
    private var questionsRepository: QuestionsRepository,
    private val answeredQuestionsRepository: AnsweredQuestionsRepository,
    private val userRepository: UserRepository
) {

    fun showTotalCountResponse(question: QuestionsEntity): ResponseEntity<QuestionsDAO> {
        val receivers = ArrayList<UserDAO>()
        for (receiver in question.receivers) {
            val student = userRepository.findByUserEmail(receiver)
            if (student.isPresent) {
                val pStudent = student.get()
                val user = UserDAO
                    .Builder()
                    .name(pStudent.name)
                    .id(pStudent.id!!)
                    .questionId(question.questionId!!)
                    .gender(pStudent.gender)
                    .email(pStudent.userEmail)
                    .dateCreated(pStudent.dateAdded)
                    .build()
                receivers.add(user)
            }
        }

        return ResponseEntity.ok(QuestionsDAO
            .Builder()
            .status(200)
            .message("success")
            .totalCounts(receivers.size)
            .students(receivers)
            .build()
        )
    }

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
     * @param questionId the question id as a parameter is used to fetch question from the database
     * @param teacherId the id of the teacher that is requesting the question
     * @return QuestionEntity
     */
    fun getSubmittedAnswers(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO> {
        getSingleQuestion(questionId, teacherId) // verify that the question exists
        val answeredQuestionsEntity: List<AnsweredQuestionsEntity> = answeredQuestionsRepository.findAnsweredQuestionsEntitiesByQuestionId(questionId)

        val receivers = ArrayList<UserDAO>()
        for (receiver in answeredQuestionsEntity) {
            val student = userRepository.findById(receiver.userId)
            if (student.isPresent) {
                val pStudent = student.get()
                val user = UserDAO
                    .Builder()
                    .name(pStudent.name)
                    .id(pStudent.id!!)
                    .questionId(questionId)
                    .gender(pStudent.gender)
                    .email(pStudent.userEmail)
                    .dateCreated(pStudent.dateAdded)
                    .build()
                receivers.add(user)
            }
        }

        return ResponseEntity.ok(QuestionsDAO
            .Builder()
            .status(200)
            .message("success")
            .totalCounts(receivers.size)
            .students(receivers)
            .build()
        )
    }

    /**
     * @param questionId the question id as a parameter is used to fetch question from the database
     * @param teacherId the id of the teacher that is requesting the question
     * @return QuestionEntity
     */
    fun getPassOrFailedStudents(questionId: String, teacherId: String, remark: String): ResponseEntity<QuestionsDAO> {
        getSingleQuestion(questionId, teacherId) // verify that the question exists
        val answeredQuestions: List<AnsweredQuestionsEntity> = answeredQuestionsRepository.findAnsweredQuestionsEntitiesByQuestionId(questionId)

        val passStudents = ArrayList<AnsweredQuestionsEntity>()
        for (student in answeredQuestions) {
            if (student.remark == remark) {
                passStudents.add(student)
            }
        }

        val receivers = ArrayList<UserDAO>()
        for (receiver in passStudents) {
            val student = userRepository.findById(receiver.userId)
            if (student.isPresent) {
                val pStudent = student.get()
                val user = UserDAO
                    .Builder()
                    .name(pStudent.name)
                    .id(pStudent.id!!)
                    .questionId(questionId)
                    .gender(pStudent.gender)
                    .email(pStudent.userEmail)
                    .dateCreated(pStudent.dateAdded)
                    .build()
                receivers.add(user)
            }
        }

        return ResponseEntity.ok(QuestionsDAO
            .Builder()
            .status(200)
            .message("success")
            .totalCounts(receivers.size)
            .students(receivers)
            .build()
        )
    }

    /**
     * This method retrieves the list of students who have missed answering a specific question.
     *
     * @param questionId the ID of the question
     * @param teacherId the ID of the teacher who created the question
     * @return a ResponseEntity containing the QuestionsDAO object with the status, message, and total count of missed users
     */
    fun getAbsentStudents(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO>{
        val missedUsers = getMissedUsers(questionId, teacherId)
        val absentStudents = ArrayList<UserDAO>()

        for (userId in missedUsers) {
            val user = userRepository.findById(userId)
            if (user.isPresent) {
                val student = user.get()
                val pStudent = UserDAO
                    .Builder()
                    .name(student.name)
                    .id(student.id!!)
                    .questionId(questionId)
                    .gender(student.gender)
                    .email(student.userEmail)
                    .dateCreated(student.dateAdded)
                    .build()
                absentStudents.add(pStudent)
            }
        }

        return ResponseEntity.ok(QuestionsDAO
            .Builder()
            .status(200)
            .message("success")
            .totalCounts(absentStudents.size)
            .students(absentStudents)
            .build()
        )
    }

    /**
     * This method retrieves the list of receiver IDs for a specific question if the question has ended.
     *
     * @param questionId the ID of the question
     * @param teacherId the ID of the teacher who created the question
     * @return a list of receiver IDs for the question
     */
    private fun getReceivers(questionId: String, teacherId: String): List<String> {
        val question = getSingleQuestion(questionId, teacherId)
        val receiverIds = ArrayList<String>()

        if (question.questionEndTime < LocalDateTime.now()) {
            for (receiver in question.receivers) {
                val user = userRepository.findByUserEmail(receiver)
                if (user.isPresent) {
                    receiverIds.add(user.get().id!!)
                }
            }
        }

        return receiverIds
    }

    /**
     * This method retrieves the list of user IDs who have missed answering a specific question.
     *
     * @param questionId the ID of the question
     * @param teacherId the ID of the teacher who created the question
     * @return a list of user IDs who have missed answering the question
     */
    private fun getMissedUsers(questionId: String, teacherId: String): List<String> {
        val missedUsers = ArrayList<String>()
        val receiverIds = getReceivers(questionId, teacherId)

        for (userId in receiverIds) {
            val answer = answeredQuestionsRepository.findByQuestionIdAndUserId(questionId, userId)
            if (!answer.isPresent) {
                missedUsers.add(userId)
            }
        }

        return missedUsers
    }
}
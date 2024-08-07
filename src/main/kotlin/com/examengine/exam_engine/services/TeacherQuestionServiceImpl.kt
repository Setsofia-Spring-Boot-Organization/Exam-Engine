package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.*
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.entities.SnapshotEntity
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.interfaces.TeacherQuestionsInterface
import com.examengine.exam_engine.repositories.QuestionsRepository
import com.examengine.exam_engine.repositories.SnapshotEntityRepository
import com.examengine.exam_engine.utilities.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeacherQuestionServiceImpl(
    private var teacherUtil: TeacherUtil,
    private var questionUtil: QuestionUtil,
    private var questionsRepository: QuestionsRepository,
    private val teacherQuestionUtil: TeacherQuestionUtil,
    private val emailService: EmailService,
    private val studentUtil: StudentUtil,
    private val dateUtil: DateUtil,
    private val snapshotEntityRepository: SnapshotEntityRepository
): TeacherQuestionsInterface {

    @Transactional
    override fun createNewQuestions(teacherId: String, questionsDTO: QuestionDetailsDTO): ResponseEntity<QuestionsDAO> {
        val user = teacherUtil.getTeacher(teacherId)
        val newQuestion = user.id?.let { questionUtil.createQuestion(it, questionsDTO) }

        try {
            val createdQuestion = questionsRepository.save(newQuestion!!)

            val studentNames = studentUtil.getStudentNames(createdQuestion)

            println(studentNames)

            /**
             * This instance of ExamNotificationDetails contains details about the exam notification,
             * including the list of student names, the name of the user who created the question,
             * the question title, the date the question was created, the time range for the question,
             * the question instructions, and the receivers of the question.
             */
            val examNotificationDetails = ExamNotificationDetails(
                studentNames,
                user.name,
                "Course Name",
                createdQuestion.questionTitle,
                dateUtil.formatDateTime(createdQuestion.dateCreated),
                "${dateUtil.formatDateTime(createdQuestion.questionStartTime)} - ${dateUtil.formatDateTime(createdQuestion.questionEndTime)}",
                createdQuestion.questionInstruction,
                createdQuestion.receivers
            )

            // send the notification
            emailService.sendExamNotification(examNotificationDetails)

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

    override fun getAllTotalCountOfAbsentStudents(questionId: String, teacherId: String): ResponseEntity<QuestionsDAO> {
        return teacherQuestionUtil.getAbsentStudents(questionId, teacherId)
    }

    override fun getQuestionOverview(questionId: String, teacherId: String): ResponseEntity<OverviewDAO> {
        val totalStudents = getAllTeacherQuestionsReceiversCount(questionId, teacherId)
        val completedStudents = getAllTotalCountOfDoneStudents(questionId, teacherId)
        val passStudents = getAllTotalCountOfPassStudents(questionId, teacherId)
        val failedStudents = getAllTotalCountOfFailedStudents(questionId, teacherId)
        val absentStudents = teacherQuestionUtil.getAbsentStudents(questionId, teacherId)

        return ResponseEntity.ok(OverviewDAO
            .Builder()
            .status(200)
            .message("success")
            .totalStudents(totalStudents.body!!.totalCounts!!)
            .completedStudents(completedStudents.body!!.totalCounts!!)
            .passStudents(passStudents.body!!.totalCounts!!)
            .failedStudents(failedStudents.body!!.totalCounts!!)
            .absentStudents(absentStudents.body!!.totalCounts!!)
            .build())
    }

    override fun getStudentSnapshot(questionId: String, studentId: String): ResponseEntity<SnapshotDAO> {
        val snapshots = snapshotEntityRepository.findSnapshotEntitiesByQuestionIdAndStudentId(questionId, studentId)

        val shots = ArrayList<SnapshotDetails>()
        for (snapshot in snapshots) {
            val snap = SnapshotDetails(
                dateCreated = snapshot.dateCreated,
                snapshot = snapshot.screenshot
            )
            shots.add(snap)
        }

        return ResponseEntity.ok(SnapshotDAO(
            200,
            "success",
            shots
        ))
    }
}
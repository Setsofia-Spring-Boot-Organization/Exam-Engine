package com.examengine.exam_engine.controllers

import com.examengine.exam_engine.dao.*
import com.examengine.exam_engine.dto.*
import com.examengine.exam_engine.services.AuthenticationServiceImpl
import com.examengine.exam_engine.services.QuestionServiceImpl
import com.examengine.exam_engine.services.ScreenshotServiceImpl
import com.examengine.exam_engine.services.TeacherQuestionServiceImpl
import com.examengine.exam_engine.supabase.SupabaseClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("exam-engine/api/v1")
class AuthenticationController(
    private val authenticationServiceImpl: AuthenticationServiceImpl,
    private val questionServiceImpl: QuestionServiceImpl,
    private val supabaseClient: SupabaseClient,
    private val teacherQuestionServiceImpl: TeacherQuestionServiceImpl,
    private val screenshotServiceImpl: ScreenshotServiceImpl
) {

    // register account
    @PostMapping("/auth/register-account")
    fun registerAccount(@RequestBody accountRegistrationDTO: AccountRegistrationDTO): ResponseEntity<Any> {
        return authenticationServiceImpl.registerAccount(accountRegistrationDTO)
    }

    // login user
    @PostMapping("/auth/login")
    fun loginAccount(@RequestBody accountLoginDTO: AccountLoginDTO): ResponseEntity<Any> {
        return authenticationServiceImpl.loginUser(accountLoginDTO)
    }




    // Generic user controller
    @GetMapping("/user/account/{userId}")
    fun getUserDetails(@PathVariable userId: String): ResponseEntity<UserDetailsDAO> {
        return authenticationServiceImpl.getUserDetails(userId)
    }






    // Teacher Controllers
    @PostMapping("/teacher/create/new-questions/{teacherId}")
    fun createNewQuestions(
        @PathVariable teacherId: String,
        @RequestBody questionDetailsDTO: QuestionDetailsDTO
    ) : ResponseEntity<QuestionsDAO>{
        return teacherQuestionServiceImpl.createNewQuestions(teacherId, questionDetailsDTO)
    }

    @GetMapping("/teacher/questions/{teacherId}")
    fun getAllTeacherQuestions(
        @PathVariable teacherId: String
    ) : ResponseEntity<AllQuestionsDAO>{
        return teacherQuestionServiceImpl.getAllTeacherQuestions(teacherId)
    }

    @GetMapping("/teacher/questions/{teacherId}/limited-details")
    fun getAllTeacherQuestionsWithLimitedResults(
        @PathVariable teacherId: String
    ) : ResponseEntity<AllQuestionsDAO>{
        return teacherQuestionServiceImpl.getAllTeacherQuestionsWithLimitedResults(teacherId)
    }

    @GetMapping("/teacher/questions/{teacherId}/receivers/total/{questionId}")
    fun getAllTeacherQuestionsReceiversCount(
        @PathVariable questionId: String,
        @PathVariable teacherId: String
    ) : ResponseEntity<QuestionsDAO>{
        return teacherQuestionServiceImpl.getAllTeacherQuestionsReceiversCount(questionId, teacherId)
    }

    @GetMapping("/teacher/questions/{teacherId}/receivers/done/{questionId}")
    fun getAllTotalCountOfDoneStudents(
        @PathVariable questionId: String,
        @PathVariable teacherId: String
    ) : ResponseEntity<QuestionsDAO>{
        return teacherQuestionServiceImpl.getAllTotalCountOfDoneStudents(questionId, teacherId)
    }

    @GetMapping("/teacher/questions/{teacherId}/receivers/pass/{questionId}")
    fun getAllTotalCountOfPassStudents(
        @PathVariable questionId: String,
        @PathVariable teacherId: String
    ) : ResponseEntity<QuestionsDAO>{
        return teacherQuestionServiceImpl.getAllTotalCountOfPassStudents(questionId, teacherId)
    }

    @GetMapping("/teacher/questions/{teacherId}/receivers/fail/{questionId}")
    fun getAllTotalCountOfFailedStudents(
        @PathVariable questionId: String,
        @PathVariable teacherId: String
    ) : ResponseEntity<QuestionsDAO>{
        return teacherQuestionServiceImpl.getAllTotalCountOfFailedStudents(questionId, teacherId)
    }

    @GetMapping("/teacher/questions/{teacherId}/receivers/absent/{questionId}")
    fun getAllTotalCountOfAbsentStudents(
        @PathVariable questionId: String,
        @PathVariable teacherId: String
    ) : ResponseEntity<QuestionsDAO>{
        return teacherQuestionServiceImpl.getAllTotalCountOfAbsentStudents(questionId, teacherId)
    }

    @GetMapping("/teacher/questions/{teacherId}/overview/{questionId}")
    fun getQuestionOverview(
        @PathVariable questionId: String,
        @PathVariable teacherId: String
    ) : ResponseEntity<OverviewDAO>{
        return teacherQuestionServiceImpl.getQuestionOverview(questionId, teacherId)
    }

    @PostMapping("/save/image") // testing
    suspend fun saveImageToDatabase(
        @RequestParam("imageFile") imageFile: List<MultipartFile>,
    ): ResponseEntity<ArrayList<String>> {
        return supabaseClient.saveImage("studentId", "questionId", imageFile)
    }

















    // Student Controllers
    @PostMapping("save/student/screenshot/{questionId}")
    fun saveStudentScreenshot(
        @PathVariable questionId: String,
        @RequestBody studentScreenshotDTO: StudentScreenshotDTO
    ): ResponseEntity<StudentScreenshotDAO> {
        return screenshotServiceImpl.saveScreenshot(questionId, studentScreenshotDTO)
    }

    @GetMapping("/student/get/questions/{studentId}")
    fun getAllStudentQuestions(
        @PathVariable studentId: String
    ) : ResponseEntity<AllQuestionsDAO> {
        return questionServiceImpl.getAllStudentQuestions(studentId)
    }

    @PostMapping("/student/answer/questions/{studentId}")
    fun answerQuestions(
        @PathVariable studentId: String,
        @RequestBody studentAnswersDTO: StudentAnswersDTO,
    ) : ResponseEntity<AnsweredQuestionsDAO> {
        return questionServiceImpl.studentAnswerQuestion(studentId, studentAnswersDTO)
    }

    @GetMapping("/student/answers/history/{studentId}")
    fun getAllAnswers(
        @PathVariable studentId: String
    ) : ResponseEntity<AnswerHistoryDAO> {
        return questionServiceImpl.getAllStudentAnswerHistory(studentId)
    }
}
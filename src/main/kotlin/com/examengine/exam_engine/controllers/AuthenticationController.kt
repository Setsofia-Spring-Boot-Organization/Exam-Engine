package com.examengine.exam_engine.controllers

import com.examengine.exam_engine.dao.*
import com.examengine.exam_engine.dto.AccountLoginDTO
import com.examengine.exam_engine.dto.AccountRegistrationDTO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import com.examengine.exam_engine.dto.StudentAnswersDTO
import com.examengine.exam_engine.services.AuthenticationServiceImpl
import com.examengine.exam_engine.services.QuestionServiceImpl
import com.examengine.exam_engine.services.TeacherQuestionServiceImpl
import com.examengine.exam_engine.supabase.SupabaseClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path

@RestController
@RequestMapping("exam-engine/api/v1")
class AuthenticationController(
    private val authenticationServiceImpl: AuthenticationServiceImpl,
    private val questionServiceImpl: QuestionServiceImpl,
    private val supabaseClient: SupabaseClient,
    private val teacherQuestionServiceImpl: TeacherQuestionServiceImpl
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




    @PostMapping("/save/image") // testing
    fun saveImageToDatabase(
        @RequestParam("imageFile") imageFile: MultipartFile,
    ):ResponseEntity<String> {
        return try {
            val tempDir: Path =
                Files.createTempDirectory("images")

            val tempFile: Path = tempDir.resolve(imageFile.originalFilename!!)
            imageFile.transferTo(tempFile.toFile())

            supabaseClient.saveImage(imageFile, imageFile.originalFilename!!)
            ResponseEntity.ok("File uploaded successfully: $tempFile")
        } catch (exception: Exception) {
            exception.printStackTrace()
            ResponseEntity("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

















    // Student Controllers
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
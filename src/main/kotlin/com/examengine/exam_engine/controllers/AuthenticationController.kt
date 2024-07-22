package com.examengine.exam_engine.controllers

import com.examengine.exam_engine.dao.*
import com.examengine.exam_engine.dto.AccountLoginDTO
import com.examengine.exam_engine.dto.AccountRegistrationDTO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import com.examengine.exam_engine.dto.StudentAnswersDTO
import com.examengine.exam_engine.services.AuthenticationServiceImpl
import com.examengine.exam_engine.services.QuestionServiceImpl
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
    private val supabaseClient: SupabaseClient
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
        return questionServiceImpl.createNewQuestions(teacherId, questionDetailsDTO)
    }

    @GetMapping("/teacher/get/questions/{teacherId}")
    fun getAllQuestions(
        @PathVariable teacherId: String
    ) : ResponseEntity<AllQuestionsDAO>{
        return questionServiceImpl.getAllTeacherQuestions(teacherId)
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
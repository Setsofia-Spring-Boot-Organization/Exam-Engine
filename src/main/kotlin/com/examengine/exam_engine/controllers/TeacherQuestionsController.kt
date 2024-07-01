package com.examengine.exam_engine.controllers

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import com.examengine.exam_engine.services.QuestionServiceImpl
import com.examengine.exam_engine.supabase.SupabaseClient
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path

@RestController
@RequestMapping("/api/v1/exam-engine/teacher")
@RequiredArgsConstructor
class TeacherQuestionsController(
    private val questionServiceImpl: QuestionServiceImpl,
    private val supabaseClient: SupabaseClient
) {

    @PostMapping("/create/new-questions/{teacherId}")
    fun createNewQuestions(
        @PathVariable teacherId: String,
        @RequestBody questionDetailsDTO: QuestionDetailsDTO
    ) : ResponseEntity<QuestionsDAO>{
        return questionServiceImpl.createNewQuestions(teacherId, questionDetailsDTO)
    }

    @GetMapping("/get/questions/{teacherId}")
    fun getAllQuestions(
        @PathVariable teacherId: String
    ) : ResponseEntity<AllQuestionsDAO>{
        return questionServiceImpl.getAllTeacherQuestions(teacherId)
    }

    @PostMapping("/save/image")
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
}
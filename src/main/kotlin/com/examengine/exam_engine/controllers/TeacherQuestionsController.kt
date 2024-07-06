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


}
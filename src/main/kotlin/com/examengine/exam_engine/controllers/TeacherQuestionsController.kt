package com.examengine.exam_engine.controllers

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import com.examengine.exam_engine.services.QuestionServiceImpl
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/exam-engine/teacher")
@RequiredArgsConstructor
class TeacherQuestionsController(
    private val questionServiceImpl: QuestionServiceImpl
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
}
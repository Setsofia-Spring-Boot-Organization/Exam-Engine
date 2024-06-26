package com.examengine.exam_engine.controllers

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.services.QuestionServiceImpl
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/exam-engine/student")
@RequiredArgsConstructor
class StudentQuestionController(
    private val questionServiceImpl: QuestionServiceImpl
) {
    @GetMapping("/get/questions/{studentId}")
    fun getAllQuestions(
        @PathVariable studentId: String
    ) : ResponseEntity<AllQuestionsDAO> {
        return questionServiceImpl.getAllStudentQuestions(studentId)
    }
}
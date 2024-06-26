package com.examengine.exam_engine.controllers

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.AnsweredQuestionsDAO
import com.examengine.exam_engine.dto.StudentAnswersDTO
import com.examengine.exam_engine.services.QuestionServiceImpl
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/answer/questions/{studentId}")
    fun answerQuestions(
        @PathVariable studentId: String,
        @RequestBody studentAnswersDTO: StudentAnswersDTO,
    ) : ResponseEntity<AnsweredQuestionsDAO> {
        return questionServiceImpl.studentAnswerQuestion(studentId, studentAnswersDTO)
    }
}
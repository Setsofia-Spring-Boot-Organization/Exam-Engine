package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.QuestionsDAO
import com.examengine.exam_engine.dto.QuestionDetailsDTO
import org.springframework.http.ResponseEntity

interface QuestionsInterface {
    fun createNewQuestions(teacherId: String, questionsDTO: QuestionDetailsDTO): ResponseEntity<QuestionsDAO>
    fun getAllTeacherQuestions(teacherId: String): ResponseEntity<AllQuestionsDAO>
    fun getAllStudentQuestions(studentId: String): ResponseEntity<AllQuestionsDAO>
}
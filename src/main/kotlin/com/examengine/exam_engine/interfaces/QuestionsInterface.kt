package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dao.AllQuestionsDAO
import com.examengine.exam_engine.dao.AnswerHistoryDAO
import com.examengine.exam_engine.dao.AnsweredQuestionsDAO
import com.examengine.exam_engine.dto.StudentAnswersDTO
import org.springframework.http.ResponseEntity

interface QuestionsInterface {
    fun getAllStudentQuestions(studentId: String): ResponseEntity<AllQuestionsDAO>
    fun studentAnswerQuestion(studentId: String, studentAnswersDTO: StudentAnswersDTO): ResponseEntity<AnsweredQuestionsDAO>
    fun getAllStudentAnswerHistory(studentId: String): ResponseEntity<AnswerHistoryDAO>
}
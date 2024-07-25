package com.examengine.exam_engine.dto

data class StudentAnswersDTO(
    var questionId: String,
    var studentAnswers: List<AnswerQuestionsDTO>,
)

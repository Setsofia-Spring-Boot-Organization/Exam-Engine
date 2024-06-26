package com.examengine.exam_engine.dto

data class QuestionsDTO(
    var questionNumber: Int,
    var question: String,
    var answer: List<AnswerDTO>
)

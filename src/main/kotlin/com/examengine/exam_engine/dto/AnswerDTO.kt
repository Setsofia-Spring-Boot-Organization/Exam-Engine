package com.examengine.exam_engine.dto

data class AnswerDTO(
    var correct: List<String>,
    var worong: List<String>
)
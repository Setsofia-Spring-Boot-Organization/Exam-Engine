package com.examengine.exam_engine.dto

import com.examengine.exam_engine.enums.QuestionsType

data class QuestionsDTO(
    var id: Int,
    var text: String,
    var type: QuestionsType,
    var options: List<String>,
    var correctAnswers: List<String>,
    var score: Int
)

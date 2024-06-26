package com.examengine.exam_engine.dao

data class AllQuestionsDAO(
    var status: Int,
    var message: String,
    var questions: List<QuestionsDAO>
)

package com.examengine.exam_engine.dao

import com.examengine.exam_engine.enums.QuestionsType

data class StudentQuestionsDAO(
    var id: Int?,
    var text: String,
    var type: QuestionsType?,
    var options: List<String>,
    var score: Int?
) {
    // Nested Builder class
    class Builder {
        private var id: Int? = null
        private var text: String = ""
        private var type: QuestionsType? = null
        private var options: List<String> = listOf()
        private var score: Int? = null


        fun id(id: Int) = apply { this.id = id }
        fun text(text: String) = apply { this.text = text }
        fun options(options: List<String>) = apply { this.options = options }
        fun type(type: QuestionsType) = apply { this.type = type }
        fun score(score: Int) = apply { this.score = score }

        fun build() = StudentQuestionsDAO(
            id,
            text,
            type,
            options,
            score
        )
    }
}

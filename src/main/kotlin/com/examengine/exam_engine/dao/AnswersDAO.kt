package com.examengine.exam_engine.dao

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class AnswersDAO (
    var id: Int?,
    var text: String,
    var type: String,
    var options: List<String>,
    var userChoice: List<String>,
    var correctAnswers: List<String>
    ) {
        class Builder {
            private var id: Int? = null
            private var text: String = ""
            private var type: String = ""
            private var userChoice: List<String> = ArrayList()
            private var options: List<String> = ArrayList()
            private var correctAnswers: List<String> = ArrayList()

            fun id(id: Int?) = apply { this.id = id }
            fun text(text: String) = apply { this.text = text }
            fun type(type: String) = apply { this.type = type }
            fun options(options: List<String>) = apply { this.options  = options}
            fun userChoice(userChoice: List<String>) = apply { this.userChoice = userChoice }
            fun correctAnswers(correctAnswers: List<String>) = apply { this.correctAnswers  = correctAnswers}

            fun build() = AnswersDAO(
                id,
                text,
                type,
                options,
                userChoice,
                correctAnswers
            )
        }
    }
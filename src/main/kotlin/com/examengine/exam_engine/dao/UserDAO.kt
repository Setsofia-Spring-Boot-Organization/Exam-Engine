package com.examengine.exam_engine.dao

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class UserDAO(
    var status: Int?,
    var message: String,
    var name: String,
    var id: String,
    var gender: String,
    var email: String,
    var contact: String,
    var dateCreated: LocalDateTime?
) {
    // Nested Builder class
    class Builder {
        private var status: Int? = null
        private var message: String = ""
        private var dateCreated: LocalDateTime? = null
        private var name: String = ""
        private var id: String = ""
        private var gender: String = ""
        private var email: String = ""
        private var contact: String = ""

        fun status(status: Int) = apply { this.status = status }
        fun message(message: String) = apply { this.message = message }
        fun dateCreated(dateCreated: LocalDateTime) = apply { this.dateCreated = dateCreated }
        fun name(name: String) = apply { this.name = name }
        fun id(id: String) = apply { this.id = id }
        fun gender(gender: String) = apply { this.gender = gender }
        fun email(email: String) = apply { this.email = email }
        fun contact(contact: String) = apply { this.contact = contact }

        fun build() = UserDAO(
            status, message, name, id, gender, email, contact, dateCreated
        )
    }
}

package com.examengine.exam_engine.enums

enum class Reasons(val label: String) {
    INPUT_FIELDS_MUST_NOT_BE_EMPTY("The fields must not be empty."),
    USER_ALREADY_EXISTS("User already exists."),
    USER_NOT_FOUND("User not found."),
    INVALID_PASSWORD("The password must no be less than 8 characters.")
}
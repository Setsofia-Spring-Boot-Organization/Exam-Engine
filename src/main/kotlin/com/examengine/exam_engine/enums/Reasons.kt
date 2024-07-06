package com.examengine.exam_engine.enums

enum class Reasons(val label: String) {
    INPUT_FIELDS_MUST_NOT_BE_EMPTY("The fields must not be empty."),
    USER_ALREADY_EXISTS("User already exists."),
    USER_NOT_FOUND("User not found."),
    INVALID_PASSWORD("The password must no be less than 8 characters."),
    ONLY_ADMINS_CAN_PERFORM_THIS_ACTION("Only admins can perform this action."),
    NO_QUESTIONS_FOUND("No questions found."),
    ONLY_STUDENTS_CAN_PERFORM_THIS_ACTION("Only students are allowed to perform this action."),
    ANSWERS_NOT_SUBMITTED("Answers not submitted."),
    NO_ANSWERS_AVAILABLE("No answers found."),
    BAD_LOGIN_CREDENTIALS("Wrong email or password"),
    ERROR_CREATING_QUESTION("An error occurred while creating your questions.")
}
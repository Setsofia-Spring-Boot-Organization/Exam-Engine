package com.examengine.exam_engine.utilities

import com.examengine.exam_engine.entities.QuestionsEntity
import com.examengine.exam_engine.entities.Users
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.enums.UserRoles
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.repositories.UserRepository
import org.springframework.stereotype.Component

@Component
class StudentUtil (
    private val userRepository: UserRepository
) {

    /**
     * This method retrieves a student user by their ID.
     * It checks if the user exists and if their role is "STUDENT".
     * If the user meets these conditions, it returns the user; otherwise, it throws a MyExceptions.
     *
     * @param studentId the ID of the student to be retrieved
     * @return the user with the specified ID if they are a student
     * @throws MyExceptions if the user is not found or is not a student
     */
    fun getStudent(studentId: String) : Users {
        val user = userRepository.findById(studentId)
        if (user.isPresent && user.get().role == UserRoles.STUDENT.name) {
            return user.get()
        }

        throw MyExceptions(Reasons.ONLY_STUDENTS_CAN_PERFORM_THIS_ACTION)
    }

    /**
     * This method retrieves the names of students who received a specific question.
     * It iterates over the list of student emails from the given QuestionsEntity, looks up each student in the userRepository by their email,
     * and adds the student's email to the list of student names if the student is found.
     *
     * @param createdQuestion the QuestionsEntity containing the list of student emails to be looked up
     * @return a list of student names who received the specified question
     */
    fun getStudentNames(createdQuestion: QuestionsEntity): List<String> {
        val studentsNames = ArrayList<String>()

        for (studentEmail in createdQuestion.receivers) {
            val student = userRepository.findByUserEmail(studentEmail)
            if (student.isPresent) {
                studentsNames.add(student.get().userEmail)
            }
        }

        return studentsNames
    }

}
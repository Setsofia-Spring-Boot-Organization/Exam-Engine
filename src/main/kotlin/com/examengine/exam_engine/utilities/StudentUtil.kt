package com.examengine.exam_engine.utilities

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

    fun getStudent(studentId: String) : Users {
        val user = userRepository.findById(studentId)
        if (user.isPresent && user.get().role == UserRoles.STUDENT.name) {
            return user.get()
        }

        throw MyExceptions(Reasons.ONLY_STUDENTS_CAN_PERFORM_THIS_ACTION)
    }

    fun getStudentByEmail(email: String) : Users {
        val user = userRepository.findByUserEmail(email)
        if (user.isPresent && user.get().role == UserRoles.STUDENT.name) {
            return user.get()
        }

        throw MyExceptions(Reasons.ONLY_STUDENTS_CAN_PERFORM_THIS_ACTION)
    }
}
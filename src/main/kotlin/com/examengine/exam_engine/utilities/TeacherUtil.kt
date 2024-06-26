package com.examengine.exam_engine.utilities

import com.examengine.exam_engine.entities.Users
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.enums.UserRoles
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.repositories.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@RequiredArgsConstructor
@Component
class TeacherUtil(
    private val userRepository: UserRepository
) {

    fun getTeacher(teacherId: String) : Users {
        val user = userRepository.findById(teacherId)
        if (user.isPresent && user.get().role == UserRoles.TEACHER.name) {
            return user.get()
        }

        throw MyExceptions(Reasons.ONLY_ADMINS_CAN_PERFORM_THIS_ACTION)
    }
}
package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.SuccessResponseDAO
import com.examengine.exam_engine.dto.AccountRegistrationDTO
import com.examengine.exam_engine.entities.Users
import com.examengine.exam_engine.enums.Reasons
import com.examengine.exam_engine.enums.UserRoles
import com.examengine.exam_engine.exceptions.MyExceptions
import com.examengine.exam_engine.interfaces.AuthenticationInterface
import com.examengine.exam_engine.repositories.UserRepository
import com.examengine.exam_engine.responses.Responses
import com.examengine.exam_engine.validations.Validations
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.UUID

@RequiredArgsConstructor
@Service
class AuthenticationServiceImpl(
    private val validation: Validations,
    private val userRepository: UserRepository,
    private val responses: Responses
) : AuthenticationInterface {

    override fun registerAccount(accountRegistrationDTO: AccountRegistrationDTO): ResponseEntity<Any> {
        if (validation.isRegistrationValid(accountRegistrationDTO)) throw MyExceptions(Reasons.INPUT_FIELDS_MUST_NOT_BE_EMPTY)
        return createAccount(accountRegistrationDTO)
    }

    private fun createAccount(accountRegistrationDTO: AccountRegistrationDTO) : ResponseEntity<Any> {
        if (validation.userIsExisting(accountRegistrationDTO)) throw MyExceptions(Reasons.USER_ALREADY_EXISTS)
        val user = Users(
            userId = UUID.randomUUID().toString(),
            userEmail = accountRegistrationDTO.getEmail(),
            password = accountRegistrationDTO.getPassword(),
            role = UserRoles.ADMIN
        )
        try {
            userRepository.save(user)
        } catch (exception: Exception) {
            throw exception
        }
        return responses.successResponse(SuccessResponseDAO(
            status = 201,
            message = "Account created successfully!"
        ))
    }
}
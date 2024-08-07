package com.examengine.exam_engine.validations

import com.examengine.exam_engine.dto.AccountLoginDTO
import com.examengine.exam_engine.dto.AccountRegistrationDTO
import com.examengine.exam_engine.interfaces.ValidationsInterface
import com.examengine.exam_engine.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class Validations(
    private val userRepository: UserRepository
) : ValidationsInterface {

    override fun isValidRegistrationField(accountRegistrationDTO: AccountRegistrationDTO): Boolean {
        return (accountRegistrationDTO.email.isEmpty() || accountRegistrationDTO.password.isEmpty() || accountRegistrationDTO.username.isEmpty())
    }

    override fun isValidLoginField(accountLoginDTO: AccountLoginDTO): Boolean {
        return (accountLoginDTO.getEmail().isEmpty() || accountLoginDTO.getPassword().isEmpty())
    }

    override fun isPasswordInvalid(password: String): Boolean {
        return (password.length < 8)
    }

    override fun userIsExisting(accountRegistrationDTO: AccountRegistrationDTO): Boolean {
        return userRepository.findByUserEmail(accountRegistrationDTO.email).isPresent
    }
}
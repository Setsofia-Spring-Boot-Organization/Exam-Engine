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
        return (accountRegistrationDTO.getEmail().isEmpty() || accountRegistrationDTO.getPassword().isEmpty())
    }

    override fun isValidLoginField(accountLoginDTO: AccountLoginDTO): Boolean {
        return (accountLoginDTO.getEmail().isEmpty() || accountLoginDTO.getPassword().isEmpty())
    }

    override fun userIsExisting(accountRegistrationDTO: AccountRegistrationDTO): Boolean {
        return userRepository.findByUserEmail(accountRegistrationDTO.getEmail()).isPresent
    }
}
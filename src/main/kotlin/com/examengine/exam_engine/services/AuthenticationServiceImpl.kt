package com.examengine.exam_engine.services

import com.examengine.exam_engine.dao.LoginResponseDAO
import com.examengine.exam_engine.dao.SignupResponseDAO
import com.examengine.exam_engine.dao.UserDetailsDAO
import com.examengine.exam_engine.dto.AccountLoginDTO
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
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@RequiredArgsConstructor
@Service
class AuthenticationServiceImpl(
    private val validation: Validations,
    private val userRepository: UserRepository,
    private val responses: Responses,
    private val authenticationManager: AuthenticationManager,
    private val jwtServiceImpl: JwtServiceImpl,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationInterface {


    override fun registerAccount(accountRegistrationDTO: AccountRegistrationDTO): ResponseEntity<Any> {
        if (validation.isValidRegistrationField(accountRegistrationDTO)) throw MyExceptions(Reasons.INPUT_FIELDS_MUST_NOT_BE_EMPTY)
        if (validation.isPasswordInvalid(accountRegistrationDTO.password)) throw MyExceptions(Reasons.INVALID_PASSWORD)
        return createAccount(accountRegistrationDTO)
    }

    private fun createAccount(accountRegistrationDTO: AccountRegistrationDTO) : ResponseEntity<Any> {
        if (validation.userIsExisting(accountRegistrationDTO)) throw MyExceptions(Reasons.USER_ALREADY_EXISTS)
        val user = Users(
            dateAdded = LocalDateTime.now(),
            name = accountRegistrationDTO.username,
            userEmail = accountRegistrationDTO.email,
            gender = accountRegistrationDTO.gender,
            userPassword = passwordEncoder.encode(accountRegistrationDTO.password),
            role = UserRoles.TEACHER.name
        )
        try {
            userRepository.save(user)
        } catch (exception: Exception) {
            throw exception
        }
        return responses.signupResponse(SignupResponseDAO(status = 201, message = "Account created successfully!"))
    }

    override fun loginUser(accountLoginDTO: AccountLoginDTO): ResponseEntity<Any> {
        if (validation.isValidLoginField(accountLoginDTO)) throw MyExceptions(Reasons.INPUT_FIELDS_MUST_NOT_BE_EMPTY)
        return login(accountLoginDTO)
    }

    override fun getUserDetails(userId: String): ResponseEntity<UserDetailsDAO> {
        val isUser = userRepository.findById(userId)
        if (isUser.isPresent) {
            return ResponseEntity.ok(UserDetailsDAO(
                200,
                "success",
                isUser.get()
            ))
        }
        throw MyExceptions(Reasons.USER_NOT_FOUND)
    }

    private fun login(accountLoginDTO: AccountLoginDTO): ResponseEntity<Any> {
        val loggedInUser = userRepository.findByUserEmail(accountLoginDTO.getEmail())
        if (loggedInUser.isPresent) {
            val user = loggedInUser.get()

            try {
                val userAuth = UsernamePasswordAuthenticationToken(
                    accountLoginDTO.getEmail(),
                    accountLoginDTO.getPassword()
                )
                try {
                    authenticationManager.authenticate(userAuth)

                    val token = jwtServiceImpl.generateTokens(user)
                    return responses.loginResponse(LoginResponseDAO(
                        status = 200,
                        message = "Login successful!",
                        userId = user.id,
                        username = user.username,
                        token = token,
                        roles = user.role
                    ))
                } catch (e: Exception) {
                    println(e)
                    throw MyExceptions(Reasons.BAD_LOGIN_CREDENTIALS)
                }
            } catch (exception: Exception) {
                throw exception
            }
        }
        throw MyExceptions(Reasons.USER_NOT_FOUND)
    }
}
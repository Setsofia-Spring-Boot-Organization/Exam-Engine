package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dto.AccountLoginDTO
import com.examengine.exam_engine.dto.AccountRegistrationDTO

interface ValidationsInterface {
    fun isValidRegistrationField(accountRegistrationDTO: AccountRegistrationDTO) : Boolean
    fun isValidLoginField(accountLoginDTO: AccountLoginDTO) : Boolean
    fun userIsExisting(accountRegistrationDTO: AccountRegistrationDTO) : Boolean
}
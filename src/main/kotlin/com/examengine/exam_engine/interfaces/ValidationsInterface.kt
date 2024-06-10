package com.examengine.exam_engine.interfaces

import com.examengine.exam_engine.dto.AccountRegistrationDTO

interface ValidationsInterface {
    fun isRegistrationValid(accountRegistrationDTO: AccountRegistrationDTO) : Boolean
    fun userIsExisting(accountRegistrationDTO: AccountRegistrationDTO) : Boolean
}
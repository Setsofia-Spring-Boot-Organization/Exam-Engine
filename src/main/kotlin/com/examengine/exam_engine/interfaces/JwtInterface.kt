package com.examengine.exam_engine.interfaces

import org.springframework.security.core.userdetails.UserDetails
import javax.crypto.SecretKey

interface JwtInterface {
    fun getSignIntKey(): SecretKey
    fun extractUsername(token: String): String
    fun generateTokens(claims: Map<String, Any>, userDetails: UserDetails): String
    fun generateTokens(userDetails: UserDetails): String
    fun tokenIsValid(token: String, userDetails: UserDetails): Boolean
}
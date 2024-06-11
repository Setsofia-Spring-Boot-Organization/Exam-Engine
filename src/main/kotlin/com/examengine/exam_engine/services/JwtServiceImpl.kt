package com.examengine.exam_engine.services

import com.examengine.exam_engine.interfaces.JwtInterface
import com.examengine.exam_engine.repositories.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Service
@RequiredArgsConstructor
class JwtServiceImpl(
    private val userRepository: UserRepository,
    @Value("\${auth-key}") private val signInKey: String
) : JwtInterface {


    override fun getSignIntKey(): SecretKey {
        val bytes: ByteArray = Decoders.BASE64.decode(signInKey)
        return Keys.hmacShaKeyFor(bytes)
    }

    // get the user id
    fun userId(email: String): String? {
        val isUser = userRepository.findByUserEmail(email).orElseThrow()
        return isUser.id
    }

    // 2. create a method that extracts all claims from the JWTs
    private fun extractAllClaimsFromToken(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignIntKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    // 3. extract a single claim from the extractAllClaimsFromToken function
    private fun <T> extractClaimsFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = extractAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    // 4. generate token
    fun generateToken(claims: Map<String, Any>, userDetails: UserDetails): String {
        try {
            return Jwts.builder()
                .claims(claims)
                .subject(userDetails.username)
                .id(userId(userDetails.username))
                .issuedAt(Date(System.currentTimeMillis()))
                .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hr when you do not tick the remember me -  1 day
                .signWith(getSignIntKey())
                .compact()
        } catch (jwtException: JwtException) {
            throw JwtException(jwtException.message)
        }
    }

    override fun extractUsername(token: String): String {
        return extractClaimsFromToken<String>(token, Claims::getSubject)
    }

    override fun generateTokens(claims: Map<String, Any>, userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)
    }

    override fun generateTokens(userDetails: UserDetails): String {
        return generateTokens(HashMap(), userDetails)
    }

    override fun tokenIsValid(token: String, userDetails: UserDetails): Boolean {
        val username = this.extractUsername(token)
        return (username == userDetails.username)
    }
}
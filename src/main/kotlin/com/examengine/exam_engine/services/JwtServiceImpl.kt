package com.examengine.exam_engine.services

import com.examengine.exam_engine.enums.UserRoles
import com.examengine.exam_engine.interfaces.JwtInterface
import com.examengine.exam_engine.repositories.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.Setter
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Service
@RequiredArgsConstructor
class JwtServiceImpl : JwtInterface {
    private val userRepository: UserRepository? = null

    @Value("\${auth-key}")
    private val SIGN_IN_KEY: String? = null
    override fun getSignIntKey(): SecretKey {
        val bytes: ByteArray = Decoders.BASE64.decode(SIGN_IN_KEY)
        return Keys.hmacShaKeyFor(bytes)
    }

    // get the user id
    @Getter
    @Setter
    private var adminID: String? = null
    @Getter
    @Setter
    private var companyName: String? = null
    fun userId(email: String): String {
        val isUser = userRepository!!.findByUserEmail(email).orElseThrow()
        if (isUser.getRole() == UserRoles.ADMIN.toString()) {
            adminID = isUser.getId()
            companyName = isUser.username
        }
        return isUser.getId()
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

    // remember me
    fun generateTokenRememberMe(claims: Map<String?, Any?>?, userDetails: UserDetails): String {
        try {
            return Jwts.builder()
                .claims(claims)
                .subject(userDetails.username)
                .id(userId(userDetails.username))
                .issuedAt(Date(System.currentTimeMillis()))
                .expiration(Date(System.currentTimeMillis() + 60000)) // 20 days when remember me is ticked
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
        return generateTokenRememberMe(HashMap(), userDetails)
    }

    override fun generateTokens(userDetails: UserDetails): String {
        return generateTokens(HashMap(), userDetails)
    }

    override fun tokenIsValid(token: String, userDetails: UserDetails): Boolean {
        val username = this.extractUsername(token)
        return (username == userDetails.username)
    }
}
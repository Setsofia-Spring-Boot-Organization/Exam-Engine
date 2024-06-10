package com.examengine.exam_engine.filters

import com.examengine.exam_engine.services.JwtServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import java.io.IOException

@Component
class JwtAuthFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtServiceImpl: JwtServiceImpl

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader: String? = request.getHeader("Authorization")

            if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                return
            }

            val token: String = authHeader.substring(7)
            val email: String = jwtServiceImpl.extractUsername(token)

            if (email.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                val user: UserDetails? = userDetailsService.loadUserByUsername(email)

                if (user != null && jwtServiceImpl.tokenIsValid(token, user)) {
                    val authenticationToken = UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.authorities
                    )
                    authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                } else {
                    handleJWTException(response, "Invalid JWT Token")
                }
            }

        } catch (exception: Exception) {
            handleJWTException(response, exception.message ?: "JWT Authentication Error")
            return
        }
        filterChain.doFilter(request, response)
    }

    @Throws(IOException::class)
    private fun handleJWTException(response: HttpServletResponse, message: String) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json"

        val errorBody: MutableMap<String, String> = HashMap()
        errorBody["message"] = message
        errorBody["status"] = HttpStatus.UNAUTHORIZED.toString()

        val mapper = ObjectMapper()
        val jsonString = mapper.writeValueAsString(errorBody)
        response.writer.write(jsonString)
    }
}

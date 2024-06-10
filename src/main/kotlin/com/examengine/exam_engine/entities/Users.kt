package com.examengine.exam_engine.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.EqualsAndHashCode
import lombok.RequiredArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "exam_engine_users")
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
data class Users (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val userId: String,
    private val username: String,
    private val userEmail: String,
    private val password: String,
    private val role: String
) : UserDetails {

    fun getId() : String {
        return this.userId
    }

    fun getName(): String {
        return this.username
    }

    override fun toString(): String {
        return "Users(userId='$userId', userEmail='$userEmail')"
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.userEmail
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Users

        if (userId != other.userId) return false
        if (userEmail != other.userEmail) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + userEmail.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }
}
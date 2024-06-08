package com.examengine.exam_engine.entities

import com.examengine.exam_engine.enums.UserRoles
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.Setter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "exam_engine_users")
@Getter
@Setter
@Builder
@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
data class Users (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val userId: String,
    private val userEmail: String,
    private val password: String,
    private val role: UserRoles
) : UserDetails {
    override fun toString(): String {
        return "Users(userId='$userId', userEmail='$userEmail')"
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
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

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + userEmail.hashCode()
        return result
    }

}
package com.examengine.exam_engine.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document
data class Users (
    @Id
    var id: String? = null,
    var name: String,
    var userEmail: String,
    var userPassword: String,
    var role: String
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }

    override fun getPassword(): String {
        return this.userPassword
    }

    override fun getUsername(): String {
       return this.userEmail
    }
}

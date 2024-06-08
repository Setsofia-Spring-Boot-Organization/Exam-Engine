package com.examengine.exam_engine.repositories

import com.examengine.exam_engine.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<Users, String> {
    fun findByUserEmail(email: String) : Optional<Users>
}
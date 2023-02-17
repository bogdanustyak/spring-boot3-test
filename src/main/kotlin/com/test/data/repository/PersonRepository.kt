package com.test.data.repository

import com.test.data.entity.EPerson
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<EPerson, Long> {

    fun <T> findById(channelId: Long?, view: Class<T>?): T?

    fun <T> findAllByNameContainingIgnoreCaseOrAge(
        name: String?,
        age: Int?,
        pageable: Pageable,
        type: Class<T>
    ): Page<T>?

    fun existsByUsernameOrEmailOrPhone(username: String, email: String, phonenumber: String): Boolean
}

package com.test.domain.dto

import com.test.data.entity.EPerson
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.sql.Date

data class RqPerson(
    @NotBlank
    @field:Size(max = 50, message = "Valid length is less then 50 characters")
    val username: String,
    @NotBlank
    @field:Size(min = 8, message = "Minimum length is 8 characters")
    val password: String,
    @NotBlank
    @Email
    val email: String,
    // TODO could be a phone number validator
    @NotBlank(message = "Please enter your phone number")
    val phone: String,
    val dateOfBirth: Date?,
    val name: String?,
    val surname: String?,
    val age: Int?
)

interface RsPerson {
    val id: Long
    val email: String
    val phone: String
    val name: String?
    val surname: String?
    val dateOfBirth: Date?
    val age: Int?
}

fun RqPerson.toEntity(): EPerson {
    return EPerson(
        name = this.name,
        surname = this.surname,
        email = this.email,
        phone = this.phone,
        dateOfBirth = this.dateOfBirth,
        age = this.age,
        username = this.username,
        password = this.password
    )
}

package com.test.domain.validation

import com.test.data.repository.PersonRepository
import org.springframework.stereotype.Component

interface PersonValidator {
    fun isPersonExists(userName: String, email: String, phoneNumber: String): Boolean
}

@Component
class PersonValidatorImpl(private val personRepository: PersonRepository) : PersonValidator {

    override fun isPersonExists(userName: String, email: String, phoneNumber: String): Boolean {
        return personRepository.existsByUsernameOrEmailOrPhone(userName, email, phoneNumber)
    }
}

package com.test.domain.validation

import com.test.domain.PersonService
import com.test.domain.dto.RqPerson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import java.sql.Date

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonValidatorImplTest @Autowired constructor(
    private val validator: PersonValidator,
    private val service: PersonService
) {

    @Test
    fun isPersonExists() {
        val userName = "some_username"
        val email = "test@gmail.com"
        val phoneNumber = "123456789"
        service.create(
            RqPerson(
                username = userName,
                name = "name",
                email = email,
                password = "password",
                phone = phoneNumber,
                surname = "surname",
                age = 29,
                dateOfBirth = Date.valueOf("1993-2-2")
            )
        )

        assertEquals(true, validator.isPersonExists(userName, email, phoneNumber))
        assertEquals(true, validator.isPersonExists(userName, "email", phoneNumber))
        assertEquals(true, validator.isPersonExists(userName, "email", "phoneNumber"))
        assertEquals(true, validator.isPersonExists("userName", email, "phoneNumber"))
        assertEquals(true, validator.isPersonExists("userName", "email", phoneNumber))


        assertEquals(false, validator.isPersonExists("userName", "email", "phoneNumber"))
    }
}

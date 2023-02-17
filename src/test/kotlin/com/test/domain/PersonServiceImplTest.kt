package com.test.domain

import com.test.data.repository.PersonRepository
import com.test.domain.dto.RqPerson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import java.sql.Date

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonServiceImplTest @Autowired constructor(
    private val service: PersonService,
    private val personRepository: PersonRepository
) {

    @Test
    fun create() {
        val user = service.create(
            RqPerson(
                username = "some_username",
                name = "name",
                email = "test@gmail.com",
                password = "password",
                phone = "123456789",
                surname = "surname",
                age = 29,
                dateOfBirth = Date.valueOf("1993-2-2")
            )
        )

        assertEquals("name", user.name)
        assertEquals("123456789", user.phone)
        assertEquals("surname", user.surname)
        assertEquals(29, user.age)
    }

    @Test
    fun getAllUsers() {
        service.create(
            RqPerson(
                username = "other_username",
                name = "name",
                email = "test_second@gmail.com",
                password = "password",
                phone = "00023456789",
                surname = "surname",
                age = 29,
                dateOfBirth = null
            )
        )
        val allUsers = service.getAllUsers(
            pageable = Pageable.ofSize(100),
            name = null,
            age = null,
        )
        assertEquals(true, !allUsers!!.isEmpty)

        val allUsersWithAge = service.getAllUsers(
            pageable = Pageable.ofSize(100),
            name = null,
            age = 29,
        )
        assertEquals(true, !allUsersWithAge!!.isEmpty)

        val allUsersWithName = service.getAllUsers(
            pageable = Pageable.ofSize(100),
            name = "name",
            age = null,
        )

        assertEquals(true, !allUsersWithName!!.isEmpty)

        val allUsersWithNameAndAge = service.getAllUsers(
            pageable = Pageable.ofSize(100),
            name = "name",
            age = 29,
        )

        assertEquals(true, !allUsersWithNameAndAge!!.isEmpty)
    }

    @Test
    fun allUsersWithNonExistingNameAndAge() {
        service.create(
            RqPerson(
                username = "non_existing_test",
                name = "some name",
                email = "non_existing_test@gmail.com",
                password = "password",
                phone = "987654321",
                surname = "surname",
                age = 30,
                dateOfBirth = null
            )
        )
        val allUsersWithNonExistingNameAndAge = service.getAllUsers(
            pageable = Pageable.ofSize(100),
            name = "NON_EXISTNG_NAME",
            age = 100,
        )

        assertEquals(true, allUsersWithNonExistingNameAndAge!!.isEmpty)
    }

    @Test
    fun update() {
        val resp = service.create(
            RqPerson(
                username = "update_user_test",
                name = "some name",
                email = "update_user_test@gmail.com",
                password = "password",
                phone = "9999999999",
                surname = "surname",
                age = 30,
                dateOfBirth = null
            )
        )

        val updatedUser = service.update(
            userId = resp.id,
            rqPerson = RqPerson(
                username = "update_user_test_updated",
                password = "new_password_updated",
                name = "some_name_updated",
                email = "update_user_test_updated@gmail.com",
                phone = "1111111111",
                dateOfBirth = Date.valueOf("1993-08-02"),
                surname = "surname_updated",
                age = 31,
            ),
        )

        val updatedUserFromDb = service.getPerson(updatedUser.id)
        assertEquals("some_name_updated", updatedUserFromDb.name)
        assertEquals("update_user_test_updated@gmail.com", updatedUserFromDb.email)
        assertEquals("1111111111", updatedUserFromDb.phone)
        assertEquals("surname_updated", updatedUserFromDb.surname)
        assertEquals(31, updatedUserFromDb.age)
        assertEquals(Date.valueOf("1993-08-02"), updatedUserFromDb.dateOfBirth)

        val updatedPerson = personRepository.findById(updatedUser.id).get()
        assertEquals("update_user_test_updated", updatedPerson.username)
        assertEquals("new_password_updated", updatedPerson.password)
    }

    @Test
    fun delete() {
        val resp = service.create(
            RqPerson(
                username = "delete_user_test",
                name = "some name",
                email = "delete_user_test@gmail.com",
                password = "password",
                phone = "22222222222",
                surname = "surname",
                age = 30,
                dateOfBirth = null
            )
        )
        service.delete(resp.id)
        assertThrows<IllegalArgumentException> { service.getPerson(resp.id) }
    }
}

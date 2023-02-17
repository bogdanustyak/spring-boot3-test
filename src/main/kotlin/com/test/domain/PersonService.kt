package com.test.domain

import com.test.data.entity.EPerson
import com.test.data.repository.PersonRepository
import com.test.domain.dto.RqPerson
import com.test.domain.dto.RsPerson
import com.test.domain.dto.toEntity
import com.test.domain.validation.PersonValidator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

interface PersonService {
    fun getAllUsers(name: String?, age: Int?, pageable: Pageable): Page<RsPerson>?
    fun create(rqPerson: RqPerson): RsPerson
    fun update(userId: Long, rqPerson: RqPerson): RsPerson
    fun delete(userId: Long)
    fun getPerson(userId: Long): RsPerson
}

@Service
class PersonServiceImpl(
    private val repository: PersonRepository,
    private val validationService: PersonValidator,
) : PersonService {

    override fun getPerson(userId: Long): RsPerson {
        return repository.findById(userId, RsPerson::class.java)
            ?: throw IllegalArgumentException("Person not found")
    }

    override fun getAllUsers(name: String?, age: Int?, pageable: Pageable): Page<RsPerson>? {
        if (name == null && age == null) {
            return repository.findAll(pageable) as Page<RsPerson>
        }
        return repository.findAllByNameContainingIgnoreCaseOrAge(
            name = name,
            age = age,
            type = RsPerson::class.java,
            pageable = pageable
        )
    }

    override fun create(rqPerson: RqPerson): RsPerson {
        require(
            !validationService.isPersonExists(
                rqPerson.username,
                rqPerson.email,
                rqPerson.phone
            )
        ) // TODO adopt message for security reasons
        { "User already exists." }

        val persisted = repository.save(rqPerson.toEntity())
        return repository.findById(persisted.id!!, RsPerson::class.java)
            ?: throw RuntimeException("Error while creating person")

    }

    override fun update(id: Long, rqPerson: RqPerson): RsPerson {
        repository.findById(id, EPerson::class.java)?.apply {
            this.age = rqPerson.age
            this.name = rqPerson.name
            this.surname = rqPerson.surname
            this.dateOfBirth = rqPerson.dateOfBirth
            this.email = rqPerson.email
            this.phone = rqPerson.phone
            this.username = rqPerson.username
            this.password = rqPerson.password
        }?.also {
            repository.save(it)
        } ?: throw RuntimeException("Person not found")

        return repository.findById(id, RsPerson::class.java)
            ?: throw RuntimeException("Error while updating person")
    }

    override fun delete(userId: Long) {
        val person = repository.findById(userId).orElseThrow {
            throw RuntimeException("Person not found")
        }
        repository.delete(person)
    }
}

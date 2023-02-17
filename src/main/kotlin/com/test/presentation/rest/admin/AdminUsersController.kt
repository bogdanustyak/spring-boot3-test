package com.test.presentation.rest.admin

import com.test.domain.PersonService
import com.test.domain.dto.RqPerson
import com.test.domain.dto.RsPerson
import com.test.presentation.rest.ApiVersion
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiVersion.ADMIN_API_V1}/users")
class AdminUsersController(private val service: PersonService) {
    
    @PostMapping("")
    fun createPerson(@Validated @RequestBody rqPerson: RqPerson): RsPerson {
        return service.create(rqPerson)
    }

    @GetMapping("/{userId}")
    fun getPerson(@PathVariable("userId") userId: Long): RsPerson {
        return service.getPerson(userId)
    }

    @PutMapping("/{userId}")
    fun updatePerson(
        @PathVariable("userId") userId: Long,
        @Validated @RequestBody rqPerson: RqPerson
    ): RsPerson {
        return service.update(userId, rqPerson)
    }

    @DeleteMapping("/{userId}")
    fun deletePerson(@PathVariable("userId") userId: Long) {
        service.delete(userId)
    }
}

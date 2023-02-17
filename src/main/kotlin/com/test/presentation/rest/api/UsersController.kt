package com.test.presentation.rest.api

import com.test.domain.PersonService
import com.test.domain.dto.RsPerson
import com.test.presentation.rest.ApiVersion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${ApiVersion.API_V1}/users")
class UsersController(private val service: PersonService) {

    @GetMapping("")
    fun getAllUsers(@RequestParam name: String?, @RequestParam age: Int?, pageable: Pageable): Page<RsPerson> {
        return service.getAllUsers(name = name, age = age, pageable = pageable)!!
    }
}

package com.test.data.entity

import jakarta.persistence.*
import java.sql.Date
import java.sql.Timestamp

@Entity
@Table(name = "Person")
data class EPerson(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(length = 50, name = "name")
    var name: String?,

    @Column(length = 50, name = "surname")
    var surname: String?,

    @Column(name = "email", unique = true, nullable = false)
    var email: String,

    @Column(name = "phone", unique = true, nullable = false)
    var phone: String,

    @Column(name = "date_of_birth")
    var dateOfBirth: Date?,

    @Column(name = "age")
    var age: Int?,

    @Column(length = 50, name = "username", unique = true, nullable = false)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,
)

package com.ssu.micropower.models.domain

data class User(
    val userId: Long,
    val userLogin: String,
    val firstName: String,
    val lastName: String
)
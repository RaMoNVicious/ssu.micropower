package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("user_login")
    val userLogin: String,
    @SerializedName("user_name")
    val firstName: String,
    @SerializedName("user_surname")
    val lastName: String,
    @SerializedName("user_role_id")
    val userRoleId: Int,
    @SerializedName("user_role")
    val userRole: String,
    val session: UserSessionDto
)
package com.ssu.micropower.models

import com.ssu.micropower.models.api.UserSessionDto
import com.ssu.micropower.models.api.UserDto
import com.ssu.micropower.models.api.UserSessionRefreshDto
import com.ssu.micropower.models.dao.UserEntity
import com.ssu.micropower.models.domain.UserSession
import com.ssu.micropower.models.domain.User
import com.ssu.micropower.models.domain.UserSessionRefresh

fun UserDto.toDomain() : User {
    return User(
        userId = this.userId,
        userLogin = this.userLogin,
        firstName = this.firstName,
        lastName = this.lastName,
    )
}

fun UserSessionDto.toDomain() : UserSession {
    return UserSession(
        sessionId = this.sessionId,
        accessToken = this.accessToken,
        accessTokenExpiry = this.accessTokenExpiry,
        refreshToken = this.refreshToken,
        refreshTokenExpiry = this.refreshTokenExpiry
    )
}

fun UserSessionRefreshDto.toDomain() : UserSessionRefresh {
    return UserSessionRefresh(
        accessToken = this.accessToken,
        accessTokenExpiry = this.accessTokenExpiry,
        refreshToken = this.refreshToken,
        refreshTokenExpiry = this.refreshTokenExpiry
    )
}

fun User.toDao() : UserEntity {
    return UserEntity(
        id = this.userId,
        userLogin = this.userLogin,
        firstName = this.firstName,
        lastName = this.lastName,
    )
}

fun UserEntity.toDomain() : User {
    return User(
        userId = this.id,
        userLogin = this.userLogin,
        firstName = this.firstName,
        lastName = this.lastName,
    )
}
package com.ssu.micropower.usecases

import com.ssu.micropower.api.Failure
import com.ssu.micropower.api.Result
import com.ssu.micropower.api.onFailure
import com.ssu.micropower.api.onSuccess
import com.ssu.micropower.app.SessionManager
import com.ssu.micropower.models.api.UserDto
import com.ssu.micropower.models.dao.ILocationDao
import com.ssu.micropower.models.dao.IUserDao
import com.ssu.micropower.models.dao.IWeatherDao
import com.ssu.micropower.models.domain.User
import com.ssu.micropower.models.domain.UserSessionRefresh
import com.ssu.micropower.models.toDao
import com.ssu.micropower.models.toDomain
import com.ssu.micropower.repositories.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: IUserRepository,
    private val userDao: IUserDao,
    private val locationDao: ILocationDao,
    private val weatherDao: IWeatherDao,
    private val sessionManager: SessionManager
) : IAuthUseCase {

    override suspend fun refresh(): Flow<Result<User>> {
        return flow {
            val session = sessionManager.getSession()
            val token = sessionManager.getRefreshToken()

            when {
                session == -1 -> {
                    sessionManager.clearSession()
                    emit(Result.Failure(Failure.AuthNeeded()))
                }
                token == null -> {
                    sessionManager.clearSession()
                    emit(Result.Failure(Failure.AuthExpired()))
                }
                else -> {
                    authRepository
                        .refresh(session, token)
                        .onSuccess {
                            updateSession(it.toDomain())
                            emit(Result.Success(userDao.getUser().toDomain()))
                        }
                        .onFailure {
                            emit(Result.Failure(it))
                        }
                }
            }
        }
    }

    override suspend fun auth(login: String, password: String): Flow<Result<User>> {
        return flow {
            authRepository
                .auth(login, password)
                .onSuccess {
                    saveUser(it)
                    emit(Result.Success(it.toDomain()))
                }
                .onFailure {
                    emit(Result.Failure(it))
                }
        }
    }

    override suspend fun logout(): Flow<Result<Boolean>> {
        return flow {
            // TODO:
            /*sessionManager.clearSession()
            userDao.clear()
            locationDao.clear()
            weatherDao.clear()*/

            emit(Result.Success(true))
        }
    }

    private fun saveUser(user: UserDto) {
        sessionManager.sessionSave(user.session.toDomain())
        userDao.insert(user.toDomain().toDao())
    }

    private fun updateSession(session: UserSessionRefresh) {
        sessionManager.sessionUpdate(session)
    }
}
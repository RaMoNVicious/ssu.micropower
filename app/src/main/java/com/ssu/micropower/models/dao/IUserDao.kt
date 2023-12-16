package com.ssu.micropower.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IUserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity): Long

    @Query("DELETE FROM user")
    fun clear()
}
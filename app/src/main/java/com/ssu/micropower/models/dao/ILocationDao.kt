package com.ssu.micropower.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ILocationDao {

    @Query("SELECT * FROM location")
    fun getLocations() : List<LocationEntity>

    @Query("SELECT * FROM location WHERE id = :locationId LIMIT 1")
    fun getLocation(locationId: Long) : LocationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: LocationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locations: List<LocationEntity>): List<Long>

    @Query("DELETE FROM location")
    fun clear()
}
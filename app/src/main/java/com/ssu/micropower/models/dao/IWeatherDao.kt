package com.ssu.micropower.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IWeatherDao {
    @Query("SELECT * FROM weather WHERE id = :locationId")
    fun getWeather(locationId: Long) : List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: WeatherEntity): Long

    @Query("DELETE FROM weather WHERE location_id == :locationId")
    fun delete(locationId: Long)

    @Query("DELETE FROM weather")
    fun clear()
}

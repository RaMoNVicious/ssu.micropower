package com.ssu.micropower.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssu.micropower.app.Constants
import com.ssu.micropower.models.dao.ILocationDao
import com.ssu.micropower.models.dao.IUserDao
import com.ssu.micropower.models.dao.IWeatherDao
import com.ssu.micropower.models.dao.LocationEntity
import com.ssu.micropower.models.dao.UserEntity
import com.ssu.micropower.models.dao.WeatherEntity
import javax.inject.Inject

@Database(
    entities = [
        UserEntity::class,
        LocationEntity::class,
        WeatherEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase @Inject constructor() : RoomDatabase() {

    abstract fun localWeatherDao(): IWeatherDao

    abstract fun localUserDao(): IUserDao

    abstract fun localLocationDao(): ILocationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                .build()
    }
}
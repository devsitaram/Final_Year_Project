package com.sitaram.foodshare.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sitaram.foodshare.utils.ApiUrl

/**
 * Database helper class for managing the Room database instance.
 * tables: ProfileEntity, FoodsEntity, HistoryEntity etc.
 */
@Database(entities = [ProfileEntity::class, FoodsEntity::class, HistoryEntity::class, NotificationEntity::class], version = 5, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {

    // Abstract method
    abstract fun userDao(): RoomDao

    companion object {
        private var INSTANCE: DatabaseHelper? = null
        // Return the database helper's instance
        fun getDatabaseInstance(context: Context): DatabaseHelper {
            synchronized(context) {
                return INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        /**
         * Builds the database instance.and context The application context to then return the database instance.
         */
        private fun buildDatabase(context: Context): DatabaseHelper {
            return Room.databaseBuilder(
                context.applicationContext,
                DatabaseHelper::class.java,
                ApiUrl.LOCAL_DATABASE_NAME
            ).fallbackToDestructiveMigration().build() // auto migrate the database
        }

        /**
         * delete the database instance.
         * @param context The application context.
         * @return The boolean response.
         */
        fun clearDatabase(context: Context): Boolean {
            val dbFile = context.getDatabasePath(ApiUrl.LOCAL_DATABASE_NAME)
            return if (dbFile.exists()) {
                dbFile.delete()
            } else {
                false
            }
        }
    }
}
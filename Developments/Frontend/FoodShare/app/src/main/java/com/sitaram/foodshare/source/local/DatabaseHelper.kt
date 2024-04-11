package com.sitaram.foodshare.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sitaram.foodshare.utils.ApiUrl

@Database(entities = [ProfileEntity::class, FoodsEntity::class, HistoryEntity::class], version = 4, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun userDao(): RoomDao

    companion object {
        private var INSTANCE: DatabaseHelper? = null

        fun getDatabaseInstance(context: Context): DatabaseHelper {
            synchronized(context) {
                return INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): DatabaseHelper {
            return Room.databaseBuilder(
                context.applicationContext,
                DatabaseHelper::class.java,
                ApiUrl.LOCAL_DATABASE_NAME
            ).fallbackToDestructiveMigration().build() // auto migrate the database
        }
    }
}
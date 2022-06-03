package com.capstone.goloak.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.goloak.model.HomeListTrash

@Database(
    entities = [HomeListTrash::class],
    version = 1,
    exportSchema = false
)
abstract class HomeListTrashDatabase : RoomDatabase() {
    abstract fun homeListTrashDao(): HomeListTrashDao

    companion object {
        @Volatile
        private var INSTANCE: HomeListTrashDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): HomeListTrashDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HomeListTrashDatabase::class.java, "list_trash_database"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

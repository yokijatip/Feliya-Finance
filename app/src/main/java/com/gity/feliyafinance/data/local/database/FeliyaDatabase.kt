package com.gity.feliyafinance.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gity.feliyafinance.data.local.dao.UserDao
import com.gity.feliyafinance.data.local.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class FeliyaDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: FeliyaDatabase? = null

        fun getDatabase(context: Context): FeliyaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeliyaDatabase::class.java,
                    "feliyafinance_db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
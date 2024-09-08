package com.gity.feliyafinance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gity.feliyafinance.data.local.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun checkUserExists(email: String): User?

    @Query("SELECT * FROM user")
    fun getAllAccounts(): Flow<List<User>>
}
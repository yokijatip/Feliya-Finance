package com.gity.feliyafinance.data

import com.gity.feliyafinance.data.local.dao.UserDao
import com.gity.feliyafinance.data.local.model.User

class Repository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.createUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun checkEmailExists(email: String): User? {
        return userDao.checkUserExists(email)
    }

    fun getAllAccounts() = userDao.getAllAccounts()
}
package com.gity.feliyafinance.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var email: String? = null,

    var password: String? = null,

    var role: String? = null,
) : Parcelable

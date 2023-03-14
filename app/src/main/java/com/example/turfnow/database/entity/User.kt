package com.example.turfnow.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user", indices = [Index(value = ["email_id"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long,
    @ColumnInfo(name ="name")
    val name:String,
    @ColumnInfo(name = "email_id")
    val email_id:String,
    @ColumnInfo(name = "phone_number")
    val phone_number:String,
    @ColumnInfo(name = "password")
    val password:String
):Parcelable

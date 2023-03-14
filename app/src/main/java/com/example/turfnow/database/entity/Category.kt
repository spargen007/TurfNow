package com.example.turfnow.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "category", indices = [Index(value = ["name"], unique = true)])
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long,
    @ColumnInfo(name = "image_url")
    val image:String,
    @ColumnInfo(name = "name")
    val name:String
)
package com.example.turfnow.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "turf", indices = [Index(value = ["name"], unique = true)])
data class Turf(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long,
    @ColumnInfo(name ="name")
    val name:String,
    @ColumnInfo(name = "location")
    val location:String,
    @ColumnInfo(name = "ratings")
    val ratings:String,
    @ColumnInfo(name = "image_url")
    val image:String
):Parcelable
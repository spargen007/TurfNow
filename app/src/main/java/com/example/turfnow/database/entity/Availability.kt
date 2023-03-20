package com.example.turfnow.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "availability", foreignKeys = [
    ForeignKey(entity = GroundType::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("ground_type_id"),
    onDelete = ForeignKey.CASCADE)], primaryKeys = ["ground_type_id","date","slot_time"]
)
data class Availability(
    @ColumnInfo(name ="ground_type_id")
    val ground_type_id:String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name="slot_time")
    val slot_time:String,
    @ColumnInfo(name = "is_booked")
    val is_booked:Boolean,
    @ColumnInfo(name = "price")
    val price:Double
):Parcelable
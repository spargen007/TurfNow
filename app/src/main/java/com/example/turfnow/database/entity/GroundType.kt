package com.example.turfnow.database.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ground_type", foreignKeys = [ForeignKey(entity = Turf::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("turf_id"),
    onDelete = ForeignKey.CASCADE),ForeignKey(entity = Category::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("category_id"),
    onDelete = ForeignKey.CASCADE),],  indices = [
    Index(value = ["turf_id", "category_id"], unique = true)
]
)
data class GroundType(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long,
    @ColumnInfo(name ="turf_id")
    val turf_id:String,
    @ColumnInfo(name = "category_id")
    val category_id:String,
    @ColumnInfo(name = "capacity")
    val capacity:String,
): Parcelable
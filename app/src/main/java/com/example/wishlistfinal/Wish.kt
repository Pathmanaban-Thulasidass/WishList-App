package com.example.wishlistfinal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    @ColumnInfo(name = "wish-title")
    var title : String,
    @ColumnInfo(name = "wish-desc")
    var description : String
)



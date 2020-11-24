package com.example.practiceproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Second(var text:String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var id: Int = 0
}
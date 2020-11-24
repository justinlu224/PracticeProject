package com.example.practiceproject.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Student")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val name: String,
    val phone: String,
    val hobby: String,
    val elseInfo: String
){



}
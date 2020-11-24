package com.example.practiceproject.room

import androidx.room.Database
import androidx.room.RoomDatabase
const val DB_NAME = "dataBase"
@Database(entities = [Student::class,Second::class], version = 8)
abstract class DataBase:RoomDatabase() {
    abstract fun getStudentDao():StudentDao
    abstract fun getSecondDao():SecondDao
}
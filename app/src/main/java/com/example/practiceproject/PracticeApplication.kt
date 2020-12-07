package com.example.practiceproject

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practiceproject.room.DB_NAME
import com.example.practiceproject.room.DataBase

class PracticeApplication:Application() {


    companion object{
        lateinit var instance:PracticeApplication
        lateinit var dataBase: DataBase
        val MEDIA_TYPE = "application/json; charset=utf-8"
        val URL = "https://data.ntpc.gov.tw"


        //資料表刪除elseInfo欄位
        val MIGRATION_1_2 = object :Migration(4,5){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE Student_a (id INTEGER , name TEXT NOT NULL, phone TEXT NOT NULL, hobby TEXT NOT NULL , PRIMARY KEY (id) )")
                database.execSQL("INSERT INTO Student_a (id, name, phone, hobby) SELECT id, name , phone ,hobby FROM Student")
                database.execSQL("DROP TABLE Student")
                database.execSQL("ALTER TABLE Student_a RENAME TO Student")
            }
        }
        //資料表新增欄位
        val MIGRATION_5_6 = object :Migration(5,6){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Student ADD COLUMN elseInfo TEXT NOT NULL DEFAULT 0")
            }
        }

        //db 新增表
//        val MIGRATION_6_7 = object :Migration(6,7){
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE IF NOT EXISTS Second (id INTEGER NOT NULL DEFAULT 0 , text TEXT NOT NULL , PRIMARY KEY (id))")
//            }
//        }

    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        dataBase = Room.databaseBuilder(this,DataBase::class.java, DB_NAME).fallbackToDestructiveMigration().build()

    }

}


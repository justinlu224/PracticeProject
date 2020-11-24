package com.example.practiceproject.room

import androidx.room.*

@Dao
interface StudentDao {

    @Query("SELECT * FROM Student")
    suspend fun getAll(): MutableList<Student>

    @Query("SELECT * FROM Student WHERE name = :name")
    suspend fun getStudentDataFromName(name: String): Student

    //Long 返回新增的主鍵
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudentData(student: Student):Long

    @Query("INSERT INTO Student (name,phone,hobby) VALUES(:name,:phone,:hobby)")
    suspend fun insertStudentData(name: String, phone: String, hobby: String)

    @Update
    suspend fun updateData(student: Student):Int

    @Query("UPDATE Student SET name = :name, phone = :phone, hobby = :hobby WHERE id = :id")
    suspend fun updateData(id: Int, name: String, phone: String, hobby: String)

    @Delete
    suspend fun deleteData(student: Student)


    //return 0 失敗 1 成功
    @Query("DELETE FROM Student WHERE id = :id")
    suspend fun deleteDataId(id: Int?):Int
}

@Dao
interface SecondDao{
    @Query("SELECT * FROM Second")
    suspend fun getAll():MutableList<Second>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSecondData(second: Second)

    @Update
    suspend fun updateData(second:Second)

    @Query("DELETE FROM Second WHERE id = :id")
    suspend fun deleteDataId(id: Int?)

}


package com.example.practiceproject.todoasync

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.practiceproject.MainActivity

class TodoListActivity : AppCompatActivity() {

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()


        val run = Runnable {

startActivity(Intent(this, MainActivity::class.java))
        }

        handler.postDelayed(run,2000)
    }
}
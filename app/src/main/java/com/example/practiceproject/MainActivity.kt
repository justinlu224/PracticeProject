package com.example.practiceproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.practiceproject.databinding.ActivityMainBinding
import com.example.practiceproject.databinding.MainAdapterBinding
import com.example.practiceproject.recyclerviewsnaphelper.RecyclerViewActivity
import com.example.practiceproject.room.RoomActivity
import com.example.practiceproject.todoasync.CoroutinesAsyncActivity
import com.example.practiceproject.viewpage2.ViewPage2Activity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding:ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            btnRoom.setOnClickListener(this@MainActivity)
            btnTodo.setOnClickListener(this@MainActivity)
            btnRecyclerview.setOnClickListener(this@MainActivity)
            btnViewPage2.setOnClickListener(this@MainActivity)
        }

        object : CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long) {
                Log.i("CountDownTimer","onTick: $millisUntilFinished")
            }

            override fun onFinish() {
                Log.i("CountDownTimer","onFinish: ")
            }

        }.start()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnRoom -> nextPage(RoomActivity::class.java)
            R.id.btnTodo -> nextPage(CoroutinesAsyncActivity::class.java)
            R.id.btnRecyclerview -> nextPage(RecyclerViewActivity::class.java)
            R.id.btnViewPage2 -> nextPage(ViewPage2Activity::class.java)
        }
    }

    fun <T>nextPage(clazz: Class<T>){
        startActivity(Intent(this,clazz))
    }

    override fun onResume() {
        super.onResume()
//        runBlocking {
//            launch {
////                goodNumbers()
//            }
//            launch {//in same thread
//                for(i in channelForResult) {
//                    println("play something $i")
//                }
//            }
//        }

        MainScope().launch {
            goodNumbers()
            for(i in channelForResult) {
                    println("play something $i")
                }
        }
    }

    val channelForResult = Channel<Int>()

    suspend fun goodNumbers(){
        listOf(9,5,2,7).forEach {
            delay(500)
            println("play something $it")
            channelForResult.send(it)
        }
        channelForResult.close()
    }


}
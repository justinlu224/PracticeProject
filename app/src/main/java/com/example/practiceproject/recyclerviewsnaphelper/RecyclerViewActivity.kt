package com.example.practiceproject.recyclerviewsnaphelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practiceproject.databinding.RecyclerViewActivityBinding

class RecyclerViewActivity : AppCompatActivity() {

    private var _binding:RecyclerViewActivityBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = RecyclerViewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
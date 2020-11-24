package com.example.practiceproject.widget

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.practiceproject.databinding.DialogCoverPageBinding

class CusDialog(context: Context) : AlertDialog(context) {
    private val binding by lazy {
        DialogCoverPageBinding.inflate(LayoutInflater.from(context))
    }

    init {
        setCancelable(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setView(binding.root)
        Glide.with(context)
            .load("https://obs.line-scdn.net/0h4pW4RF2da19qTUJwFKoUCEoZZz8VY3BdA3N0JxEVYAFHeSQAUiImP0xENW1HfC4KUS8nPE9FNmlEfi0LEyp2MEpFZT9G/w360")
            .into(binding.imgCover)
        show()

        binding.btnClose.setOnClickListener {
            cancel()
        }
    }
}
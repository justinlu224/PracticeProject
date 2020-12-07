package com.example.practiceproject.utils

import android.os.Looper
import com.example.practiceproject.PracticeApplication
import com.example.practiceproject.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

object Utils: AnkoLogger {

    fun handleException(ex: Exception) {
        when (ex) {
            is UnknownHostException -> {
                showToastInMainThread(PracticeApplication.instance.resources.getString(R.string.no_network))
            }
            is HttpException -> {
                showErrorMessage(ex.response()?.errorBody()?.string())
            }
            else -> {
                info("system error: ${ex.message}")
            }
        }
    }

    private fun showErrorMessage(message: String?) {
        if (message?.isNotEmpty() == true) {
            info("api exception: $message")
            try {
                val msg = JSONObject(message).getString("errMsg")
                showToastInMainThread(msg)
                info("error msg: $msg")
            } catch (ex: Exception) {
                showToastInMainThread(message)
                info("exception showErrorMessage: ${ex.message}")
            }
        }
    }

    private fun showToastInMainThread(message: String){
        if (Thread.currentThread() == Looper.getMainLooper().thread){
            PracticeApplication.instance.toast(message)
        }
    }

}
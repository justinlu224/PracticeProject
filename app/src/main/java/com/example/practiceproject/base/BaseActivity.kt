package com.example.practiceproject.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.practiceproject.utils.Utils.handleException
import java.lang.Exception

abstract class BaseActivity<VM:BaseViewModel>: AppCompatActivity() {

    abstract val aViewModel:VM
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

}

open class BaseViewModel: ViewModel() {

    inline fun callApi(apiFunction: () -> Unit) = callApi(apiFunction, {})

    inline fun callApi(apiFunction: () -> Unit, errorFunction: (Exception) -> Unit, showDefaultErrorMessage: Boolean = true) {
        try {
            apiFunction()
        } catch (ex: Exception) {
            if (showDefaultErrorMessage) {
                handleException(ex)
            }
            errorFunction(ex)
        }
    }

    inline fun callResultApi(apiFunction: () -> BaseResponse<*>): Boolean {
        return try {
            val response = apiFunction()
            response.isSuccess()
        } catch (ex: Exception) {
            handleException(ex)
            false
        }
    }

    inline fun <T> callResponseApi(apiFunction: () -> BaseResponse<T>): T? {
        return try {
            val response = apiFunction()
            if (response.isSuccess()) {
                response.data
            } else {
                null
            }
        } catch (ex: Exception) {
            handleException(ex)
            null
        }
    }

}

data class BaseResponse<T>(var status: String? = "", var msg: String? = "", var data: T? = null) {
    fun isSuccess(): Boolean = status.equals("success")
}

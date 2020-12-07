package com.example.practiceproject.base

import android.content.Context
import androidx.fragment.app.Fragment
import java.lang.Exception

abstract class BaseFragment<T:BaseActivity<*>, VM: BaseViewModel>: Fragment() {

    abstract val fViewModel:VM
    var rootActivity:T? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        rootActivity = try {
            activity as? T
        }catch (e:Exception){
            null
        }
    }

    override fun onDetach() {
        super.onDetach()
        rootActivity = null
    }
}
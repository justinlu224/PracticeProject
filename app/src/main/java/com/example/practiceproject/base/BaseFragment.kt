package com.example.practiceproject.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.Exception

abstract class BaseFragment<T:BaseActivity<*>, VM: BaseViewModel, VB: ViewBinding>: Fragment() {

    abstract val fViewModel:VM
    var rootActivity:T? = null
    var _binding:VB? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        rootActivity = try {
            activity as? T
        }catch (e:Exception){
            null
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        rootActivity = null
    }
}
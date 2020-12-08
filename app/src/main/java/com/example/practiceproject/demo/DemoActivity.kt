package com.example.practiceproject.demo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.MainActivity
import com.example.practiceproject.PracticeApplication
import com.example.practiceproject.UBikeData
import com.example.practiceproject.api.RetrofitManager
import com.example.practiceproject.base.BaseActivity
import com.example.practiceproject.base.BaseViewModel
import com.example.practiceproject.databinding.ActivityDemoBinding
import com.example.practiceproject.databinding.ItemDemoBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class DemoActivity : BaseActivity<DemoViewModel>() {

    private val demoAdapter by lazy {
        DemoAdapter()
    }

    override val aViewModel: DemoViewModel
        get() = ViewModelProvider(this).get(DemoViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        with(binding) {
//            recyclerView.layoutManager = LinearLayoutManager(this@DemoActivity)
//            recyclerView.adapter = demoAdapter
//        }


        aViewModel.ubickDatas.observe(this, Observer {
            demoAdapter.demoList = it
        })
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            aViewModel.getUBikeData()
//            aViewModel.postUBikeData()

        }
    }
}

class DemoAdapter : RecyclerView.Adapter<DemoViewHolder>() {
    var demoList = listOf<UBikeData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
        return DemoViewHolder(
            ItemDemoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = demoList.size

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        val item = demoList[position]
        holder.binding.tvTitle.text = item.sarea
        holder.binding.tvSubtitle.text = item.sna
    }
}

class DemoViewHolder(val binding: ItemDemoBinding) : RecyclerView.ViewHolder(binding.root)


class DemoViewModel : BaseViewModel() {

    val title = MutableLiveData<String>()

    val ubickDatas = MutableLiveData<List<UBikeData>>()

    suspend fun getUBikeData() = callApi {
        val response = RetrofitManager.apiService.getUBikeInfo()
        ubickDatas.postValue(response)
    }

    suspend fun postUBikeData() = callApi {
        val user = User("Justin", 18)
        val gson = Gson()
        val strJson = gson.toJson(user)
        Log.i("http", "postUBikeData: $strJson")

        val map = mapOf("name" to "Justin" , "age" to 18)
        Log.i("http", "postUBikeData: $map ,,,,,, ${gson.toJson(map)}")
        val requestBody =
            RequestBody.create(MediaType.parse(PracticeApplication.MEDIA_TYPE), gson.toJson(map))
        RetrofitManager.apiService.postUBikeInfo(requestBody)
    }

}

data class User(val name: String, val age: Int)
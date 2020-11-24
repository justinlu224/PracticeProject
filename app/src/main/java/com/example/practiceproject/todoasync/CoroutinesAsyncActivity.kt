package com.example.practiceproject.todoasync

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiceproject.widget.CusDialog
import com.example.practiceproject.databinding.ActivityMain2Binding

import kotlinx.coroutines.*

const val TAG = "MainActivity"

class CoroutinesAsyncActivity : AppCompatActivity() {

    var a: String? = null

    var mList = mutableListOf<String?>()

    var b: String? = null

    var asyncA: String? = null

    var asyncB: String? = null

    var c: String? = null

    var d: String? = null

    lateinit var binding: ActivityMain2Binding

    var mainAdapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        mainAdapter = MainAdapter()
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(
                this@CoroutinesAsyncActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                DividerItemDecoration(
                    this@CoroutinesAsyncActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = mainAdapter
        }

        val viewModel = ViewModelProvider(this).get<MainActivityViewModel>()

        binding.button.setOnClickListener {
            viewModel.onNewTodo.postValue(Unit)
        }

        viewModel.todoLiveData.observe(this, Observer {
            mainAdapter?.list = it.toMutableList()
        })

        val tt = intent?.extras?.getString("123") ?: ""

        Log.i(TAG, "onCreate: $tt")

        val screenWidth = windowManager.defaultDisplay.width
        val screenHeight = windowManager.defaultDisplay.height

        var dm = DisplayMetrics()
        dm = resources.displayMetrics

        val desityDPI = dm.densityDpi

        Log.i(TAG, "onCreate: 真實解析度: $screenWidth * $screenHeight  每英寸: $desityDPI")
        CusDialog(this)

//        val job = CoroutineScope(Dispatchers.IO).launch {
//            a = loadA()
//            b = loadB()
//            Log.i(TAG, "onCreate not async: A: ${a} , B: ${b}")
//        }

//        CoroutineScope(Dispatchers.IO).launch {
//            asyncA = async { loadAsyncA() }.await()
//            asyncB = async { loadAsyncB() }.await()
//            Log.i(TAG, "onCreate: asyncA: ${asyncA} , asyncB: ${asyncB}")
//        }

        CoroutineScope(Dispatchers.IO).launch {
            val c = async { loadC() }
            val d = async { loadD() }

            val list = (0 until 10).map {
                Log.i(TAG, "onCreate: it: $it")
                async {
                    //可以直接將結果存放在list中 依照api回來順序 與執行順序無關 ：相關原始碼請看awaitAll
//                    when (it) {
//                        0 -> {
//                            Log.i(TAG, "onCreate: it 0: $it")
//                            mList.add(loadB())
//                        }
//                        1 -> {
//                            Log.i(TAG, "onCreate: it 1: $it")
//                            mList.add(loadC())
//                        }
//                        2 -> {
//                            Log.i(TAG, "onCreate: it 2: $it")
//                            mList.add(loadD())
//                        }
//                        3 -> {
//                            Log.i(TAG, "onCreate: it 3: $it")
//                            mList.add(loadD())
//                        }
//                        4 -> {
//                            Log.i(TAG, "onCreate: it 4: $it")
//                            mList.add(loadC())
//                        }
//                        5 -> {
//                            Log.i(TAG, "onCreate: it 5: $it")
//                            mList.add(loadB())
//                        }
//                        6 -> {
//                            Log.i(TAG, "onCreate: it 6: $it")
//                            mList.add(loadA())
//                        }
//                        7 -> {
//                            Log.i(TAG, "onCreate: it 7: $it")
//                            mList.add(loadC())
//                        }
//                        8 -> {
//                            Log.i(TAG, "onCreate: it 8: $it")
//                            mList.add(loadD())
//                        }
//                        9 -> {
//                            Log.i(TAG, "onCreate: it 9: $it")
//                            mList.add(loadA())
//                        }
//                        else -> mList.add("112233")
//                    }

                    //有序的寫法 上面錯誤
                    when (it) {
                        0 -> {
                            Log.i(TAG, "onCreate: it 0: $it")
                            loadB()
                        }
                        1 -> {
                            Log.i(TAG, "onCreate: it 1: $it")
                            loadC()
                        }
                        2 -> {
                            Log.i(TAG, "onCreate: it 2: $it")
                            loadD()
                        }
                        3 -> {
                            Log.i(TAG, "onCreate: it 3: $it")
                            loadD()
                        }
                        4 -> {
                            Log.i(TAG, "onCreate: it 4: $it")
                            loadC()
                        }
                        5 -> {
                            Log.i(TAG, "onCreate: it 5: $it")
                            loadB()
                        }
                        6 -> {
                            Log.i(TAG, "onCreate: it 6: $it")
                            loadA()
                        }
                        7 -> {
                            Log.i(TAG, "onCreate: it 7: $it")
                            loadC()
                        }
                        8 -> {
                            Log.i(TAG, "onCreate: it 8: $it")
                            loadD()
                        }
                        9 -> {
                            Log.i(TAG, "onCreate: it 9: $it")
                            loadA()
                        }
                        else -> "112233"
                    }
                }
            }.awaitAll()

            Log.i(TAG, "onCreate: list: ${mList} , bList$list")



            Log.i(TAG, "onCreate: C: ${c.await()}")
            Log.i(TAG, "onCreate: D: ${d.await()}")
            Log.i(TAG, "onCreate: C: ${c.await()}")
            Log.i(TAG, "onCreate: C: ${c.await()} , D: ${d.await()}")
        }
    }

    fun <T, R> Iterable<T>.mapTest(transform: (T) -> R): List<R> {
        val destination = ArrayList<R>()        //1
        for (item in this)
            destination.add(transform(item))    //2
        return destination
    }


    suspend fun loadA(): String? {
        Log.i(TAG, "loadA1")
        delay(40000)
        Log.i(TAG, "loadA2")
        return "loadA finish"
    }

    suspend fun loadB(): String? {
        Log.i(TAG, "loadB1: A: $a")
        delay(2000)
        Log.i(TAG, "loadB2: A: $a")
        return "loadB finish"
    }

    suspend fun loadAsyncA(): String? {
        Log.i(TAG, "loadAsyncA1")
        delay(40000)
        Log.i(TAG, "loadAsyncA2")
        return "loadAsyncA finish"
    }

    suspend fun loadAsyncB(): String? {
        Log.i(TAG, "loadAsyncB1: asyncA: $asyncA")
        delay(2000)
        Log.i(TAG, "loadAsyncB2: asyncA: $asyncA")
        return "loadAsyncB finish"
    }

    suspend fun loadC(): String? {
        Log.i(TAG, "loadC1")
        delay(40000)
        Log.i(TAG, "loadC2")


        return "loadC finish"
    }

    suspend fun loadD(): String? {
        Log.i(TAG, "loadD1: C: $c")
        delay(2000)
        Log.i(TAG, "loadD2: C: $c")
        return "loadD finish"
    }
}

data class Todo(val title: String, var isCheck: Boolean)




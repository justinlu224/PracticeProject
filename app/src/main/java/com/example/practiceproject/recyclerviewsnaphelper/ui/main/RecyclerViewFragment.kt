package com.example.practiceproject.recyclerviewsnaphelper.ui.main

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.R
import com.example.practiceproject.TestApplication
import com.example.practiceproject.databinding.ItemImageBinding
import com.example.practiceproject.databinding.RecyclerViewFragmentBinding

fun Context.getDrawableCom(res: Int):Drawable {

   var drawable = ContextCompat.getDrawable(TestApplication.instance, res)!!
    val rect = Rect(0, 0, drawable?.intrinsicHeight ?: 0, drawable?.intrinsicWidth ?: 0)
    drawable?.bounds = rect
    return drawable
}


class RecyclerViewFragment : Fragment() , MyAdapter.RecyclerviewFragmentListener {

    val imageList = listOf(
        TestApplication.instance.getDrawableCom(R.drawable.images),
        TestApplication.instance.getDrawableCom(R.drawable.images1),
        TestApplication.instance.getDrawableCom(R.drawable.images2),
        TestApplication.instance.getDrawableCom(R.drawable.images3),
        TestApplication.instance.getDrawableCom(R.drawable.images4),
        TestApplication.instance.getDrawableCom(R.drawable.images5),
        TestApplication.instance.getDrawableCom(R.drawable.images6),
        TestApplication.instance.getDrawableCom(R.drawable.images7),
        TestApplication.instance.getDrawableCom(R.drawable.images8),
        TestApplication.instance.getDrawableCom(R.drawable.images9)
    )
    private var _binding: RecyclerViewFragmentBinding? = null
    val binding get() = _binding!!
    lateinit var myImageAdapter: MyAdapter
    lateinit var myAdapter: MyAdapter

    private lateinit var viewModel: RecyclerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RecyclerViewFragmentBinding.inflate(layoutInflater)
        myAdapter = MyAdapter()
        myImageAdapter = MyAdapter()
        with(binding){
            recyclerView.adapter = myImageAdapter
            val myLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            recyclerView.layoutManager = myLayoutManager
                myImageAdapter.list = imageList
            myImageAdapter.recyclerviewFragmentListener = this@RecyclerViewFragment

            //PagerSnapHelper 一次滑動一頁
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(recyclerView)

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val centerView = pagerSnapHelper.findSnapView(myLayoutManager)
                        centerView?.let {
                            val pos = myLayoutManager.getPosition(it)
                            Log.i("onClick", "onScrollStateChanged: currentItem$pos")
                        }
                    }
                }
            })





            snapHelperRecyclerView.adapter = myAdapter
            snapHelperRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            myAdapter.list = imageList
//            myAdapter.recyclerviewFragmentListener = this@RecyclerViewFragment



            //LinearSnapHelper 可以像pageview捲動效果一樣 使當前item居中顯示 並可以一次捲動多個項目
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(snapHelperRecyclerView)

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get<RecyclerViewModel>()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onClick(position: Int) {
        Log.i("onClick", "onClick: $position")
//        binding.recyclerView.smoothScrollToPosition(position+1)
//        binding.snapHelperRecyclerView.smoothScrollToPosition(position)
    }

}

class MyAdapter:RecyclerView.Adapter<MyViewHolder>(){
    var list = listOf<Drawable?>()
    var recyclerviewFragmentListener:RecyclerviewFragmentListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return MyViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val image = list[position]
        holder.binding.image.setImageDrawable(image)
        holder.itemView.setOnClickListener {
            recyclerviewFragmentListener?.onClick(position)
        }
        holder.itemView.setOnFocusChangeListener { v, hasFocus ->

        }
    }

    override fun getItemCount(): Int = list.size

    interface RecyclerviewFragmentListener{
        fun onClick(position:Int)
    }
}

class MyViewHolder(val binding:ItemImageBinding):RecyclerView.ViewHolder(binding.root)

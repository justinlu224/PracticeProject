package com.example.practiceproject.room

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.PracticeApplication
import com.example.practiceproject.databinding.ActivityRoomBinding
import com.example.practiceproject.databinding.ItemRoomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

const val TAG = "RoomActivitypptty"

class RoomActivity : AppCompatActivity(), onItemClick {

    private var position: Int? = null
    private var _binding: ActivityRoomBinding? = null
    private val binding get() = _binding!!
    private val adapter = RoomAdapter()
    private var student:Student? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            recyclerView.layoutManager =
                LinearLayoutManager(this@RoomActivity, RecyclerView.VERTICAL, false)
            adapter.itemClick = this@RoomActivity
            recyclerView.adapter = adapter


            buttonCreate.onClick {
                Log.i(
                    TAG, "onCreate: ${
                        PracticeApplication.dataBase.getStudentDao().insertStudentData(
                            Student(
                                null,
                                editTextName.text.toString(),
                                editTextPhone.text.toString(),
                                editTextHobby.text.toString(),
                                editTextElse.text.toString()
                            )
                        )
                    }, ${PracticeApplication.dataBase.getSecondDao().insertSecondData(Second(editTest.text.toString()))}"
                )
                adapter.second = PracticeApplication.dataBase.getSecondDao().getAll()
                adapter.students = PracticeApplication.dataBase.getStudentDao().getAll()
            }
            buttonModify.onClick {
                Log.i(TAG, "onCreate: ${PracticeApplication.dataBase.getStudentDao().updateData(Student(
                    student?.id,
                    editTextName.text.toString(),
                    editTextPhone.text.toString(),
                    editTextHobby.text.toString(),
                    editTextElse.text.toString()
                ))}")
                adapter.students = PracticeApplication.dataBase.getStudentDao().getAll()
            }
            buttonClear.onClick {
                with(binding) {
                    editTextName.setText("")
                    editTextPhone.setText("")
                    editTextHobby.setText("")
                    editTextElse.setText("")
                }

            }

        }


        MainScope().launch {
            adapter.second = PracticeApplication.dataBase.getSecondDao().getAll()
            adapter.students = PracticeApplication.dataBase.getStudentDao().getAll()
            Log.i(TAG, "onCreate: ${adapter.students}")
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onItemClickListener(position: Int) {
        student = adapter.students?.get(position)
        this.position = position
        with(binding) {
            val item = adapter.students?.get(position)
            editTextName.setText(item?.name)
            editTextPhone.setText(item?.phone)
            editTextHobby.setText(item?.hobby)
            editTextElse.setText(item?.elseInfo)
        }

    }

    override fun onItemLongClickListener(position: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            adapter.students = withContext(Dispatchers.IO) {
                adapter.students?.get(position)?.id?.run {
                    PracticeApplication.dataBase.getStudentDao().deleteDataId(this)
                }
                PracticeApplication.dataBase.getStudentDao().getAll()
            }
        }
    }

   inline fun <T>handleClick(crossinline function:(T) -> Unit) = object : onItemClickGet<T>{
       override fun onItemClickListener(position: T) {
           function(position)
       }
   }
}

class RoomAdapter : RecyclerView.Adapter<RoomViewHolder>() {

    var students: MutableList<Student>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var second: MutableList<Second>? = null


    var itemClick: onItemClick? = null

    var handleClick: onItemClickGet<Int>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            ItemRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {

        val item = students?.get(position)
        val secondItem = second?.getOrNull(position)
        holder.binding.tvName.text = item?.name
        holder.binding.tvPhone.text = item?.phone
        holder.binding.tvHobby.text = item?.hobby
        holder.binding.tvElseInfo.text = item?.elseInfo

        holder.binding.tvTest.text = secondItem?.text

        holder.itemView.onClick {
            itemClick?.onItemClickListener(position)
        }

        holder.itemView.onLongClick {
            itemClick?.onItemLongClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return students?.size ?: 0
    }

}

class RoomViewHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root)

interface onItemClick {
    fun onItemClickListener(position: Int)
    fun onItemLongClickListener(position: Int)
}

interface onItemClickGet<T> {
    fun  onItemClickListener(position: T)
}
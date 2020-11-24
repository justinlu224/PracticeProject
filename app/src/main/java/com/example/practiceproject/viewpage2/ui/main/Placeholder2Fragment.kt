package com.example.practiceproject.viewpage2.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.practiceproject.R
import com.example.practiceproject.viewpage2.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A placeholder fragment containing a simple view.
 */
class Placeholder2Fragment : BaseFragment() {

     lateinit var pageViewModel: PageViewModel
    var msg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_view_page22, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        val imageView:ImageView = root.findViewById(R.id.image222)
        lifecycleScope.launch(Dispatchers.Default){
            delay(4000)
            withContext(Dispatchers.Main){
                textView.text = msg
                imageView.background = resources.getDrawable(R.drawable.ic_baseline_history_24)
            }
        }
        pageViewModel.text.observe(viewLifecycleOwner, Observer<String> {
           msg  = it
        })

        Log.i("Placeholder2Fragment", "onCreateView: ")


        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): Placeholder2Fragment {
            return Placeholder2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
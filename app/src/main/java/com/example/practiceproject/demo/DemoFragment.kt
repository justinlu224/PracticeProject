package com.example.practiceproject.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.practiceproject.R
import com.example.practiceproject.base.BaseFragment
import com.example.practiceproject.databinding.FragmentDemoBinding

class DemoFragment : BaseFragment<DemoActivity,DemoViewModel,FragmentDemoBinding>(){
    override val fViewModel: DemoViewModel
        get() = ViewModelProvider(requireActivity()).get(DemoViewModel::class.java)

   val binding get() = _binding!!
    private val demoAdapter by lazy {
        DemoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDemoBinding.inflate(inflater,container,false)
        binding.viewmodel = fViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = demoAdapter
        }

        fViewModel.ubickDatas.observe(viewLifecycleOwner, Observer {
            demoAdapter.demoList = it
            fViewModel.title.value = it.first().sarea
        })
    }
}
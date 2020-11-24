package com.example.practiceproject.viewpage2

import android.app.Activity
import android.os.Bundle
import android.util.SparseArray
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.practiceproject.R
import com.example.practiceproject.databinding.ActivityViewPage2Binding
import com.example.practiceproject.viewpage2.ui.main.Placeholder2Fragment
import com.example.practiceproject.viewpage2.ui.main.PlaceholderFragment
import com.example.practiceproject.viewpage2.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewPage2Activity : AppCompatActivity() {
    lateinit var sectionsPagerAdapter:SectionsPagerAdapter
    lateinit var binding:ActivityViewPage2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPage2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter


    }

    override fun onResume() {
        super.onResume()
        MainScope().launch {


//            val st = SparseArray<BaseFragment>()
//            st.put(0,PlaceholderFragment.newInstance(1))
//            st.put(1,PlaceholderFragment.newInstance(2))
//            sectionsPagerAdapter.sLoist = st

            val layoutTypes = listOf(TabData(LayoutType.PLACEHOLDER,"position: "), TabData(LayoutType.PLACEHOLDER2,"position: ###: "),
                TabData(LayoutType.PLACEHOLDER,"position: "), TabData(LayoutType.PLACEHOLDER2,"position: ###: "),
                TabData(LayoutType.PLACEHOLDER,"position: "), TabData(LayoutType.PLACEHOLDER2,"position: ###: "),
                TabData(LayoutType.PLACEHOLDER,"position: "), TabData(LayoutType.PLACEHOLDER2,"position: ###: "),
                TabData(LayoutType.PLACEHOLDER,"position: "), TabData(LayoutType.PLACEHOLDER2,"position: ###: "))
            delay(2000)

            val fragments = mutableListOf<Fragment>()

            layoutTypes.forEachIndexed { index, tabData ->
                when(tabData.layoutType){
                    LayoutType.PLACEHOLDER -> {
                        fragments.add(PlaceholderFragment.newInstance(0))
                    }
                    LayoutType.PLACEHOLDER2 -> {
                        fragments.add(Placeholder2Fragment.newInstance(1))
                    }
                }
            }
            sectionsPagerAdapter.fragmentList = fragments
            TabLayoutMediator(binding.tabs,binding.viewPager){ tab , position ->
                tab.text = "${layoutTypes[position].text}, $position"
            }.attach()
        }
    }
}

data class TabData(var layoutType:LayoutType, var text: String)

enum class LayoutType{
    PLACEHOLDER, PLACEHOLDER2
}
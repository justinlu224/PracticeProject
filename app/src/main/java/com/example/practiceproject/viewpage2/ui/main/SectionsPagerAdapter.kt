package com.example.practiceproject.viewpage2.ui.main

import android.app.Activity
import android.content.Context
import android.util.SparseArray
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practiceproject.R
import com.example.practiceproject.viewpage2.BaseFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

//    var fragmentList:MutableList<Fragment>
//
//    override fun getItem(position: Int): Fragment {
//        // getItem is called to instantiate the fragment for the given page.
//        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1)
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return context.resources.getString(TAB_TITLES[position])
//    }
//
//    override fun getCount(): Int {
//        // Show 2 total pages.
//        return 2
//    }

    var fragmentList:MutableList<Fragment> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var sLoist:SparseArray<BaseFragment> = SparseArray()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
//        return when(fragmentList[position]){
//            is PlaceholderFragment -> fragmentList[position] as PlaceholderFragment
//            else -> Fragment()
//        }

        return fragmentList[position]
    }
}

package com.example.chatboi.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    private val fragmentList = arrayListOf<Fragment>()
    private val titleList = arrayListOf<String>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment,title:String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
    fun getPage(position:Int):CharSequence?{
        return titleList[position]
    }

}
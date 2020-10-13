package tech.yashtiwari.videorecorder.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import tech.yashtiwari.videorecorder.fragment.VideoListFragment
import tech.yashtiwari.videorecorder.fragment.VideoRecordSettingsFragment

public class PagerAdapter(val fragmentActivity: FragmentActivity ) :FragmentStateAdapter(fragmentActivity){

    private val NUMBER_OF_FRAGMENT = 2;

    override fun getItemCount(): Int {
        return NUMBER_OF_FRAGMENT;
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> VideoRecordSettingsFragment.newInstance()
            1 -> VideoListFragment.newInstance()
            else -> TODO("handle other fragment values")
        }
    }

}
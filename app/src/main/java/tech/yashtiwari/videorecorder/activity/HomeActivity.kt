package tech.yashtiwari.videorecorder.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.adapter.PagerAdapter

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide();

        viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Record"
                1 -> "Gallery"
                else -> TODO("Provide the functionality for $position")
            }
        }.attach()
    }

}
package tech.yashtiwari.videorecorder.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_video_list.*
import kotlinx.android.synthetic.main.fragment_video_list.view.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.Utility
import tech.yashtiwari.videorecorder.adapter.VideoListAdapter

class VideoListFragment : Fragment() {

    private val NUMBER_COLUMN = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val viewManager = GridLayoutManager(requireContext(), NUMBER_COLUMN)
        val viewAdapter = VideoListAdapter(Utility.getMediaDuration(requireContext(), Utility.getOutputDirectory(requireContext())))
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)
        view.recyclerView .apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoListFragment()
    }

}
package tech.yashtiwari.videorecorder.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.FileObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_video_list.view.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.Utility
import tech.yashtiwari.videorecorder.adapter.VideoListAdapter
import java.io.File


class VideoListFragment : Fragment() {

    private val NUMBER_COLUMN = 2
    private lateinit var viewAdapter : VideoListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val viewManager = GridLayoutManager(requireContext(), NUMBER_COLUMN)
        viewAdapter = VideoListAdapter(Utility.getVideoList(requireContext(), Utility.getOutputDirectory(requireContext())))
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)
        view.recyclerView .apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        // Work around - right now couldnt implement event listener
        viewAdapter.addNewList(Utility.getVideoList(requireContext(), Utility.getOutputDirectory(requireContext())))
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoListFragment()

        private val TAG = "VideoListFragment"
    }

}
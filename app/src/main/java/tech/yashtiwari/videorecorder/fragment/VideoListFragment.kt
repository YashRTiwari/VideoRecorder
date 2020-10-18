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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_video_list.*
import kotlinx.android.synthetic.main.fragment_video_list.view.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.Utility
import tech.yashtiwari.videorecorder.adapter.VideoListAdapter
import tech.yashtiwari.videorecorder.viewmodels.VMVideoList
import java.io.File


class VideoListFragment : Fragment() {

    private val NUMBER_COLUMN = 2
    private lateinit var viewAdapter : VideoListAdapter
    private val viewModel : VMVideoList by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)
        viewAdapter = VideoListAdapter()
        observeLiveData()
        return view
    }

    private fun observeLiveData(){
        viewModel.mlVideoList.observe(viewLifecycleOwner, Observer { t ->
            rvVideoList.apply {
                val viewManager = GridLayoutManager(requireContext(), NUMBER_COLUMN)
                setHasFixedSize(true)
                layoutManager = viewManager
                viewAdapter.addNewList(t)
                adapter = viewAdapter
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkIfNewFileIsAdded()
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoListFragment()

        private val TAG = "VideoListFragment"
    }

}
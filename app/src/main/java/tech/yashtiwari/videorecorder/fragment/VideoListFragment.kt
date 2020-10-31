package tech.yashtiwari.videorecorder.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_video_list.*
import kotlinx.android.synthetic.main.fragment_video_list.view.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.VideoModel
import tech.yashtiwari.videorecorder.activity.PlayerActivity
import tech.yashtiwari.videorecorder.adapter.VideoListAdapter
import tech.yashtiwari.videorecorder.db.Media
import tech.yashtiwari.videorecorder.viewmodels.VMVideoList


class VideoListFragment : Fragment(), VideoListAdapter.ClickHandler {

    private val NUMBER_COLUMN = 2
    private lateinit var viewAdapter : VideoListAdapter
    private val viewModel : VMVideoList by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)
        val viewManager = GridLayoutManager(requireContext(), NUMBER_COLUMN)
        viewAdapter = VideoListAdapter(this)

        view.rvVideoList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        observeLiveData()
        return view
    }

    private fun observeLiveData(){
        viewModel.mlMediaList.observe(viewLifecycleOwner, Observer { t ->
            viewAdapter.addNewList(t)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoListFragment()
        private val TAG = "VideoListFragment"
    }

    override fun onVideoClicked(model: Media) {
        val intent = Intent(activity, PlayerActivity::class.java)
        intent.putExtra("media", model)
        startActivity(intent)
    }

}
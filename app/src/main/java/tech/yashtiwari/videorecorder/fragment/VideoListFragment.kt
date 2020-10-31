package tech.yashtiwari.videorecorder.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_video_list.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.VideoModel
import tech.yashtiwari.videorecorder.activity.PlayerActivity
import tech.yashtiwari.videorecorder.adapter.VideoListAdapter
import tech.yashtiwari.videorecorder.viewmodels.VMVideoList


class VideoListFragment : Fragment(), VideoListAdapter.ClickHandler {

    private val NUMBER_COLUMN = 2
    private lateinit var viewAdapter : VideoListAdapter
    private val viewModel : VMVideoList by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)
        viewAdapter = VideoListAdapter(this)
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

    override fun onVideoClicked(model: VideoModel) {
        val intent = Intent(activity, PlayerActivity::class.java)
        intent.putExtra("path", model.file.absolutePath)
        startActivity(intent)
    }

}
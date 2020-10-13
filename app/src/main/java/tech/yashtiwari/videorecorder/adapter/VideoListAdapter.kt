package tech.yashtiwari.videorecorder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.VideoModel

class VideoListAdapter(val items : ArrayList<VideoModel>) : RecyclerView.Adapter<VideoListAdapter.MyViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): VideoListAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_video_list_item, parent, false)

        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
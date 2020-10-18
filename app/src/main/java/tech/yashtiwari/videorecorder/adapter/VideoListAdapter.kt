package tech.yashtiwari.videorecorder.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.rv_video_list_item.view.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.VideoModel
import tech.yashtiwari.videorecorder.databinding.RvVideoListItemBinding
import java.io.File

class VideoListAdapter() : RecyclerView.Adapter<VideoListAdapter.MyViewHolder>()  {

    private var items : ArrayList<VideoModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): VideoListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_video_list_item, parent, false)
        return MyViewHolder(view)
    }

    fun addNewFile(file : VideoModel){
        items.add(0, file)
        notifyItemInserted(0)
    }

    fun addNewList(list : ArrayList<VideoModel>){
        items = list;
        notifyDataSetChanged()
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        fun bind(video : VideoModel){
            with(view){
                tvName.text = video.name
                tvDuration.text = video.duration
                video.time?.apply{
                    tvTime.text = this
                }
                Glide.with(context)
                    .load(Uri.fromFile(video.file))
                    .thumbnail(0.1F)
                    .into(videoThumbnail)
            }
        }

    }

}
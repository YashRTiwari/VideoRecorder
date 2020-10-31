package tech.yashtiwari.videorecorder.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.rv_video_list_item.view.*
import tech.yashtiwari.videorecorder.MediaType
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.VideoModel
import tech.yashtiwari.videorecorder.db.Media
import java.io.File

class VideoListAdapter(val clickHandler: ClickHandler) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Media> = ArrayList()
    private val IMAGE = 0
    private val VIDEO = 1

    interface ClickHandler {
        public fun onVideoClicked(model: Media)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            IMAGE -> ImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_image_list_item, parent, false)
            )
            VIDEO -> VideoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_video_list_item, parent, false)
            )
            else -> TODO("Invalid Media Type")
        }
    }

    fun addNewList(list: List<Media>) {
        items = list;
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            MediaType.PICTURE.name -> IMAGE
            MediaType.VIDEO.name -> VIDEO
            else -> TODO("Invalid Media Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (items[position].type) {
            MediaType.VIDEO.name -> (holder as VideoViewHolder).bind(items[position], clickHandler)
            MediaType.PICTURE.name -> (holder as ImageViewHolder).bind(items[position], clickHandler)
        }
    }

    override fun getItemCount() = items.size

    class VideoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(video: Media, handler: ClickHandler) {
            with(view) {
                tvName.text = video.name
                tvDuration.text = video.duration.toString()
                tvTime.text = video.time
                Glide.with(context)
                    .load(Uri.fromFile(File(video.path)))
                    .thumbnail(0.1F)
                    .into(videoThumbnail)

                videoItem.setOnClickListener { _ -> handler.onVideoClicked(video) }
            }
        }

    }

    class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(video: Media, handler: ClickHandler) {
            with(view) {
                tvName.text = video.name
                tvTime.text = video.time
                Glide.with(context)
                    .load(Uri.fromFile(File(video.path)))
                    .thumbnail(0.1F)
                    .into(videoThumbnail)

                videoItem.setOnClickListener { _ ->
                    handler.onVideoClicked(video) }
            }
        }

    }

}
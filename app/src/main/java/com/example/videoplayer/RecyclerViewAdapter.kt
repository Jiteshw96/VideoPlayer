package com.example.videoplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.databinding.CardviewItemVideoBinding
import com.google.android.youtube.player.*

class RecyclerViewAdapter(private val videos: List<String>): RecyclerView.Adapter<RecyclerViewAdapter.VideoViewHolder<YoutubeVideo>>() {


    private lateinit var listener : YouTubeThumbnailView.OnInitializedListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder<YoutubeVideo> {

        return VideoViewHolder(CardviewItemVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
            return videos.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder<YoutubeVideo>, position: Int) {

        val itemViewHolder = holder as VideoViewHolder
       // itemViewHolder.textView.text = "Hello"

        listener = object : YouTubeThumbnailView.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubeThumbnailView?,
                loader: YouTubeThumbnailLoader?
            ) {
                loader?.setVideo(videos.get(position))
            }

            override fun onInitializationFailure(
                p0: YouTubeThumbnailView?,
                p1: YouTubeInitializationResult?
            ) {

            }


        }

        holder.youtubePlayer.initialize(YoutubeConfig.getAPIKey(),listener)

    }

     class VideoViewHolder<YoutubeVideo>(var itemBinding:CardviewItemVideoBinding):RecyclerView.ViewHolder(itemBinding.root){
        // var textView:TextView = itemBinding.textView
         var youtubePlayer:YouTubeThumbnailView = itemBinding.youTubePlayerView

    }
}
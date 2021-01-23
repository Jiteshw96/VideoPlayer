package com.example.videoplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.utils.YoutubeConfig
import com.example.videoplayer.model.YoutubeVideo
import com.example.videoplayer.databinding.CardviewItemVideoBinding
import com.google.android.youtube.player.*
import com.example.videoplayer.contract.OnPlayListItemClick

class RecyclerViewAdapter(val videos: List<YoutubeVideo>, val onPlayListItemClick: OnPlayListItemClick): RecyclerView.Adapter<RecyclerViewAdapter.VideoViewHolder<YoutubeVideo>>() {


    private lateinit var listener : YouTubeThumbnailView.OnInitializedListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder<YoutubeVideo> {

        return VideoViewHolder(CardviewItemVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
            return videos.size -1
    }

    override fun onBindViewHolder(holder: VideoViewHolder<YoutubeVideo>, position: Int) {

        listener = object : YouTubeThumbnailView.OnInitializedListener{
            override fun onInitializationSuccess(
                    thumbnail: YouTubeThumbnailView?,
                    loader: YouTubeThumbnailLoader?
            ) {
                loader?.setVideo(videos.get(position).url)
            }

            override fun onInitializationFailure(
                    p0: YouTubeThumbnailView?,
                    p1: YouTubeInitializationResult?
            ) {

            }

        }
        holder.textView.text = videos[position].name
        holder.youtubePlayer.initialize(YoutubeConfig.getAPIKey(),listener)
        holder.youtubePlayer.setOnClickListener {
            onPlayListItemClick.playVideo(videos[position].url.toString(),position,videos[position].id)
        }
        holder.views.text = videos[position].views
        holder.likes.text = videos[position].likes
        //holder.comments.text = videos[position].comments


    }

     class VideoViewHolder<YoutubeVideo>(itemBinding:CardviewItemVideoBinding):RecyclerView.ViewHolder(itemBinding.root)
     {
         var textView:TextView = itemBinding.include2.videoDesc
         var youtubePlayer:YouTubeThumbnailView = itemBinding.youTubePlayerView
         var views = itemBinding.include2.viewsNumber
         var comments = itemBinding.include2.commentNumber
         var likes = itemBinding.include2.likeNumber

     }


}
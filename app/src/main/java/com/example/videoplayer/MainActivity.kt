package com.example.videoplayer

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.videoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private lateinit var url:Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialiMediaPlayer()
        extractYoutubeUrl()

    }

    private fun initialiMediaPlayer() {
        simpleExoplayer = SimpleExoPlayer.Builder(this).build()
        binding.videoPlayer.player = simpleExoplayer
        buildMediaSource()?.let {
            simpleExoplayer.prepare(it)
        }
    }

    private fun buildMediaSource():MediaSource? {
        val dataSourceFactory = DefaultDataSourceFactory(this,"sample")
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
               Uri.parse("https://www.youtube.com/watch?v=lTRiuFIWV54" ))
    }

    override fun onResume() {
        super.onResume()
        simpleExoplayer.playWhenReady=  true
    }

    override fun onStop() {
        super.onStop()
        simpleExoplayer.playWhenReady = false
        if(isFinishing){
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        simpleExoplayer.release()

    }

    private fun extractYoutubeUrl(){
        @SuppressLint("StaticFieldLeak")
        val extracter = object:YouTubeExtractor(this){
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {

                val str = ytFiles
                Uri.parse(ytFiles?.get(22)?.url)
            }

        }
        extracter.extract("https://www.youtube.com/watch?v=inOugVa_NYA",true,true)



    }


}
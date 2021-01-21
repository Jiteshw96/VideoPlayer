package com.example.videoplayer


import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videoplayer.databinding.ActivityMainBinding
import com.google.android.youtube.player.*


class MainActivity : YouTubeBaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listener : YouTubePlayer.OnInitializedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listener = object : YouTubePlayer.OnInitializedListener{

             override fun onInitializationSuccess(
                 provider: YouTubePlayer.Provider?,
                 youTubePlayer: YouTubePlayer,
                 bool: Boolean
             ) {
                 youTubePlayer.cueVideo("BTYAsjAVa3I")

             }

             override fun onInitializationFailure(
                 provider: YouTubePlayer.Provider?,
                 result: YouTubeInitializationResult?
             ) {

             }

        }


       setRecyclerView()

    }

    private fun setRecyclerView() {
        val list = listOf<String>("z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA")
        val recyclerViewAdapter  = RecyclerViewAdapter(list)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.adapter = recyclerViewAdapter


    }

    override fun onResume() {
        initializePlayer()
        super.onResume()

    }

    private fun initializePlayer() {
        binding.video.initialize(YoutubeConfig.getAPIKey(),listener)
    }

    override fun onStop() {
        super.onStop()
    }


}
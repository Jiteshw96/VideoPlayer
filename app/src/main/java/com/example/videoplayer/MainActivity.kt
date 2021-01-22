package com.example.videoplayer


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer.databinding.ActivityMainBinding
import com.example.videoplayer.databinding.BottomSheetCommentsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer


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
       setUpBottomSheet()

    }

    private fun setUpBottomSheet() {
       // val includedView: View = binding.include.comments
        binding.include.commentBtn.setOnClickListener {
            val bottomSheetDialog:BottomSheetDialog = BottomSheetDialog(this)
            val bottomView = layoutInflater.inflate(R.layout.bottom_sheet_comments,null)
            val binding = BottomSheetCommentsBinding.inflate(layoutInflater,bottomView as ViewGroup ,false)
            bottomSheetDialog.setContentView(binding.root)
            val list = listOf("z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA")
            val commentAdapter =  CommentListAdapter(list)
            binding.commentList.layoutManager = LinearLayoutManager(this)
            binding.commentList.adapter = commentAdapter
            binding.commentList.setHasFixedSize(true)
            val dividerItemDecoration = DividerItemDecoration(
                binding.commentList.context,
                LinearLayoutManager.VERTICAL
            )
            binding.commentList.addItemDecoration(dividerItemDecoration)
            bottomSheetDialog.show()


        }
    }

    private fun setRecyclerView() {
        val list = listOf("z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA","z44CLCafepA")
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
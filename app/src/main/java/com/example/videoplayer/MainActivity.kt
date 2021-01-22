package com.example.videoplayer


import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer.databinding.ActivityMainBinding
import com.example.videoplayer.databinding.BottomSheetCommentsBinding
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
                 youTubePlayer.cueVideo("NMFGuy6TRqk")

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
            val bottomSheetBinding = BottomSheetCommentsBinding.inflate(layoutInflater,bottomView as ViewGroup ,false)
            bottomSheetDialog.setContentView(bottomSheetBinding.root)
            val list = listOf("BwuLxPH8IDs","4qq0GQPkfjI","4qq0GQPkfjI","BOHK_w09pVA","qc_QNQzMSCE","o-ins1nvbDg")
            val commentAdapter =  CommentListAdapter(list)
            bottomSheetBinding.commentList.layoutManager = LinearLayoutManager(this)
            bottomSheetBinding.commentList.adapter = commentAdapter
            bottomSheetBinding.commentList.setHasFixedSize(true)
            val dividerItemDecoration = DividerItemDecoration(
                bottomSheetBinding.commentList.context,
                LinearLayoutManager.VERTICAL
            )
            bottomSheetBinding.commentList.addItemDecoration(dividerItemDecoration)
            bottomSheetBinding.commentBtn.setOnClickListener {
                bottomSheetDialog.hide()
            }
            bottomSheetDialog.show()

        }
    }

    private fun setRecyclerView() {
        val list = listOf("BwuLxPH8IDs","4qq0GQPkfjI","FrteWKKVyzI","BOHK_w09pVA","qc_QNQzMSCE","o-ins1nvbDg")
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
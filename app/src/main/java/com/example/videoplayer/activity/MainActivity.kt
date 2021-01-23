package com.example.videoplayer.activity


import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.videoplayer.*
import com.example.videoplayer.adapter.RecyclerViewAdapter
import com.example.videoplayer.contract.OnPlayListItemClick
import com.example.videoplayer.database.AppDatabase
import com.example.videoplayer.databinding.ActivityMainBinding
import com.example.videoplayer.databinding.BottomSheetCommentsBinding
import com.example.videoplayer.utils.YoutubeConfig
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : YouTubeBaseActivity(), OnPlayListItemClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listener: YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerObject: YouTubePlayer
    private lateinit var db: AppDatabase
    private lateinit var bottomSheetBinding: BottomSheetCommentsBinding
    private var currentVideoId = 0
    private lateinit var  commentAdapter: CommentListAdapter
    private lateinit var currentVideoComments:List<String>
    private lateinit var  bottomSheetDialog: BottomSheetDialog
    private  val INITIAL_VIDEO = "2rO4r-JOQtA"
    private val INITIAL_VIDEO_ID = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "video_items.db").build()
        loadVideo(INITIAL_VIDEO)
        currentVideoId = INITIAL_VIDEO_ID
        initializePlayer()
        addVideosToDatabase()

    }

    private fun loadVideo(videoId: String) {
        listener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                youTubePlayer: YouTubePlayer,
                bool: Boolean
            ) {
                youtubePlayerObject = youTubePlayer
                youTubePlayer.loadVideo(videoId)
                binding.include.videoDesc.text = YoutubeConfig.youtubeVideos.last().name
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {
                Log.i("result", result.toString())
            }

        }
        binding.video.initialize(YoutubeConfig.getAPIKey(), listener)

    }


    private fun setUpBottomSheet(data: List<String>) {
        bottomSheetDialog = BottomSheetDialog(this)
        val bottomView = layoutInflater.inflate(R.layout.bottom_sheet_comments, null)
        bottomSheetBinding = BottomSheetCommentsBinding.inflate(layoutInflater, bottomView as ViewGroup, false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.commentList.layoutManager = LinearLayoutManager(this)
        commentAdapter = CommentListAdapter(data)
        bottomSheetBinding.commentList.adapter = commentAdapter
        bottomSheetBinding.commentList.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(
            bottomSheetBinding.commentList.context,
            LinearLayoutManager.VERTICAL
        )
        bottomSheetBinding.commentList.addItemDecoration(dividerItemDecoration)
        bottomSheetBinding.commentHeader.setOnClickListener {
            bottomSheetDialog.hide()
        }

        bottomSheetBinding.addComment.setOnClickListener {
            GlobalScope.launch {
                insertComment()
            }
        }
        binding.include.showComments.setOnClickListener {
            bottomSheetDialog.show()
           // getComments()
        }

    }

    private suspend fun insertComment() {
        if (bottomSheetBinding.userComment.text.isNotEmpty()) {
            val comment = bottomSheetBinding.userComment.text.toString()

            GlobalScope.async {
                val data: List<String> = db.YoutubeDao().getComments(currentVideoId)
                val commentList = getListFromJson(data[0])
                commentList.add(0, comment)
                db.YoutubeDao().updateComments(commentList, currentVideoId)
            }.await()
            getComments()
            updateCommentRecyclerView()
        }

    }

    private fun updateCommentRecyclerView(){
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                //Update UI
                bottomSheetDialog.hide()

                Toast.makeText(applicationContext,"Comment Added",Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun setRecyclerView() {
        val recyclerViewAdapter = RecyclerViewAdapter(YoutubeConfig.youtubeVideos, this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = recyclerViewAdapter


    }


    private fun initializePlayer() {
        binding.video.initialize(YoutubeConfig.getAPIKey(), listener)
    }

    override fun playVideo(url: String, position: Int,videoId:Int) {
        youtubePlayerObject.loadVideo(url)
        Collections.swap(
            YoutubeConfig.youtubeVideos,
            YoutubeConfig.youtubeVideos.lastIndex,
            position
        )
        setRecyclerView()
        currentVideoId = videoId
        binding.include.videoDesc.text = YoutubeConfig.youtubeVideos.last().name
        getComments()

    }

    fun addVideosToDatabase() {
        GlobalScope.launch {
            if (db.YoutubeDao().getAll().isEmpty()) {
                db.YoutubeDao().insertAll(YoutubeConfig.youtubeVideos)
            }
            addInitialComments()
        }
    }

    fun addInitialComments() {
        GlobalScope.launch {
            val data: List<String> = db.YoutubeDao().getComments(currentVideoId)
            val comments: ArrayList<String> = getListFromJson(data[0])

            if (comments.size< 2) {
                val list = arrayListOf(
                    resources.getString(R.string.comment_1),
                    resources.getString(R.string.comment_1),
                    resources.getString(R.string.comment_2),
                    resources.getString(R.string.comment_3),
                    resources.getString(R.string.comment_4),
                    resources.getString(R.string.comment_5)
                )
                for (i in 1..7) {
                    db.YoutubeDao().updateComments(list, i)
                }
            }
            getComments()

        }

    }

    private fun getComments() {
        GlobalScope.launch {
            val data: List<String> = db.YoutubeDao().getComments(currentVideoId)
            val comments: ArrayList<String> = getListFromJson(data[0])

            withContext(Dispatchers.Main) {
                //Update UI
                setUpBottomSheet(comments)
            }

        }
    }

    /*fun getAllVideos() {
        GlobalScope.launch {
        val  data =   db.YoutubeDao().getAll()

            data?.forEach{
                Log.i("YoutubeVideo",it.comments.toString())
            }
        }
    }
*/
    fun getListFromJson(json: String): ArrayList<String> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {

        }.type
        val stringList: ArrayList<String> = gson.fromJson(json, type)
        return stringList
    }
}
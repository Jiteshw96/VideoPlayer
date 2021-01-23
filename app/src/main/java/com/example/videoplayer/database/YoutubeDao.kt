package com.example.videoplayer.database

import androidx.room.*
import com.example.videoplayer.model.YoutubeVideo


@Dao
interface YoutubeDao {
    @Query("SELECT * FROM video_items")
    fun getAll():List<YoutubeVideo>

    @Query("SELECT comments from video_items where id LIKE :id")
    fun getComments(id:Int):List<String>

    @Insert
    fun insertAll(videos:List<YoutubeVideo>)

    @Query("UPDATE video_items SET comments= :comments WHERE id = :id")
    fun updateComments(comments:ArrayList<String>,id:Int)


}
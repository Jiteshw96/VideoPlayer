package com.example.videoplayer.contract

interface OnPlayListItemClick {
    fun playVideo(id:String,position:Int,videoId:Int)
}
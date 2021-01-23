package com.example.videoplayer.utils

import com.example.videoplayer.model.YoutubeVideo

class YoutubeConfig {

    companion object{
        private val API_KEY = "AIzaSyBTQ4r3y8SwW54OmzjrTolb3AXkiBwvT8w"
        fun getAPIKey():String{
            return API_KEY
        }
        val youtubeVideos = listOf<YoutubeVideo>(
                YoutubeVideo("BwuLxPH8IDs","TypeScript Course for Beginners 2020","Learn TypeScript","356","36",
                    arrayListOf<String>("Thanks for tutorial"),1),
                YoutubeVideo("4qq0GQPkfjI","React Native Crash Course 2020","Learn React Native","326","54",arrayListOf<String>("Thanks for tutorial"),2),
                YoutubeVideo("FrteWKKVyzI","Architecture components - introduction (Google I/O '17)","Learn Android","476","94",arrayListOf<String>("Thanks for tutorial"),3),
                YoutubeVideo("BOHK_w09pVA","Understand Kotlin Coroutines on Android (Google I/O'19)","Learn Kotlin","676","12",arrayListOf<String>("Thanks for tutorial"),4),
                YoutubeVideo("qc_QNQzMSCE","Level up with Data Binding (Android Dev Summit '18)","Learn DataBinding","176","21",arrayListOf<String>("Thanks for tutorial"),5),
                YoutubeVideo("o-ins1nvbDg","Guide to Dependency Injection on Android","Learn DI","276","89",arrayListOf<String>("Thanks for tutorial"),6),
                YoutubeVideo("2rO4r-JOQtA","Fun with LiveData (Android Dev Summit '18)","Learn LiveData","376","26",arrayListOf<String>("Thanks for tutorial"),7)
        )

    }



}
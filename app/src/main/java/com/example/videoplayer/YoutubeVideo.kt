package com.example.videoplayer

import android.os.Parcel
import android.os.Parcelable

data class YoutubeVideo(var url: String?, var name:String?, var desc:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(name)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<YoutubeVideo> {
        override fun createFromParcel(parcel: Parcel): YoutubeVideo {
            return YoutubeVideo(parcel)
        }

        override fun newArray(size: Int): Array<YoutubeVideo?> {
            return arrayOfNulls(size)
        }
    }

}
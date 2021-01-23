package com.example.videoplayer.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

@Entity(tableName = "video_items")
data class YoutubeVideo(
        @ColumnInfo(name = "url") var url: String?,
        @ColumnInfo(name = "name") var name: String?,
        @ColumnInfo(name = "desc") var desc: String?,
        @ColumnInfo(name = "views") var views: String?,
        @ColumnInfo(name = "likes") var likes: String?,
        @ColumnInfo(name = "comments") var comments: ArrayList<String>?,
        @PrimaryKey @ColumnInfo(name = "id") var id: Int
) : Parcelable {


    constructor(parcel: Parcel) : this(
            url = parcel.readString(),
            name = parcel.readString(),
            desc = parcel.readString(),
            views = parcel.readString(),
            likes = parcel.readString(),
            comments = parcel.createStringArrayList(),
            id = parcel.readInt()

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeString(views)
        parcel.writeString(likes)
        parcel.writeStringList(comments)
        parcel.writeInt(id)
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
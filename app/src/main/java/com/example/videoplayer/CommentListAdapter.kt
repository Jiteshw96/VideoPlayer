package com.example.videoplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.databinding.BottomSheetCommentsBinding
import com.example.videoplayer.databinding.CommentItemBinding

class CommentListAdapter(private val commentList: List<String>): RecyclerView.Adapter<CommentItemViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
      return CommentItemViewHolder(CommentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
      return commentList.size
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        holder.text.text = commentList.get(position)
    }

}

class CommentItemViewHolder(var itemBinding:CommentItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
    val text = itemBinding.commentText



}

package com.example.assignment1

import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment1.databinding.ItemPostBinding

class PostAdapter(
    private val posts: List<Post>,
    private val onLikeClick: (Int) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        with(holder.binding) {
            tvUsername.text = post.username
            tvCaption.text = post.caption
            tvLikes.text = "${post.likes} likes"

            Glide.with(root.context)
                .load(post.imageRes)
                .into(imgPost)

            btnLike.setImageResource(
                if (post.isLiked) R.drawable.ic_like_filled else R.drawable.ic_like
            )

            btnLike.setOnClickListener { onLikeClick(post.id) }

            val gestureDetector = GestureDetector(root.context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent): Boolean {
                        if (!post.isLiked) {
                            onLikeClick(post.id)
                        }
                        return true
                    }
                })

            imgPost.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                true
            }
        }
    }

    override fun getItemCount() = posts.size
}

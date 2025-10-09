package com.example.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val posts = mutableListOf<Post>()
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val imageUrls = listOf(
            "https://i.ibb.co.com/0RRC4W58/insta1.jpg",
            "https://i.ibb.co.com/LdZDNZT0/insta2.jpg",
            "https://i.ibb.co.com/v69gyVvL/insta3.jpg",
            "https://i.ibb.co.com/Nnd56KRh/insta4.jpg",
            "https://i.ibb.co.com/DgWBKbvh/insta5.jpg"
        )

        for (i in 1..5) {
            posts.add(
                Post(
                    id = i,
                    username = "user$i",
                    caption = "This is caption for post $i",
                    imageRes = imageUrls.random(),
                    likes = (100..10000).random()
                )
            )
        }

        adapter = PostAdapter(posts) { id ->
            val post = posts.find { it.id == id }
            post?.let {
                if (it.isLiked) {
                    it.isLiked = false
                    it.likes -= 1
                } else {
                    it.isLiked = true
                    it.likes += 1
                }
                adapter.notifyItemChanged(posts.indexOf(it))
            }
        }

        recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("posts", ArrayList(posts))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedPosts = savedInstanceState.getParcelableArrayList<Post>("posts")
        if (savedPosts != null) {
            posts.clear()
            posts.addAll(savedPosts)
            adapter.notifyDataSetChanged()
        }
    }
}
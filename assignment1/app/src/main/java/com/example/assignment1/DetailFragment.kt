package com.example.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment1.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val post = DetailFragmentArgs.fromBundle(requireArguments()).post
        binding.tvPostUsername.text = post.username
        binding.tvPostCaption.text = post.caption

        val comments = listOf(
            Comment("alina", "Отличный пост! 🔥", R.drawable.story_ani),
            Comment("timur", "Класс, поддерживаю!", R.drawable.story_ani),
            Comment("dina", "Ха-ха, Compose реально позже 😄", R.drawable.story_ani),
            Comment("artem", "Жду новых постов 👀", R.drawable.story_ani)
        )

        commentAdapter = CommentAdapter(comments)
        binding.rvComments.layoutManager = LinearLayoutManager(requireContext())
        binding.rvComments.adapter = commentAdapter

        return binding.root
    }
}

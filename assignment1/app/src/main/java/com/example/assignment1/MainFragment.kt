package com.example.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment1.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var postAdapter: PostAdapter
    private val posts = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        posts.addAll(
            listOf(
                Post(1, "saiman", "Первый пост!", "https://picsum.photos/300", 23),
                Post(2, "yerassyl", "Люблю Android 💚", "https://picsum.photos/400", 42),
                Post(3, "devman", "Jetpack Compose? maybe later.", "https://picsum.photos/500", 7)
            )
        )

        postAdapter = PostAdapter(posts) { post ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(post)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }

        return binding.root
    }
}

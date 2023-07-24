package com.example.ansocialmess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ansocialmess.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {
    private var postDao: PostDao = PostDao()
    private lateinit var binding: ActivityCreatePostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.postButton.setOnClickListener {
            val input = binding.postInput.text.toString().trim()
            if (input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }
        }
    }
}
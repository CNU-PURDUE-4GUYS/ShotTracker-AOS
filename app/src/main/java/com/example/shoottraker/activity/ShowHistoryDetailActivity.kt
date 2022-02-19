package com.example.shoottraker.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoottraker.databinding.ActivityShowHistoryDetailBinding

class ShowHistoryDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityShowHistoryDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}

package com.example.shoottraker.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shoottraker.databinding.ActivityShowHistoryDetailBinding

class ShowHistoryDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityShowHistoryDetailBinding.inflate(layoutInflater)
    }

    private var copyUri: String? = null
    private var totalBullet: String? = null
    private var averageSize: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        copyUri = intent.getStringExtra("copyUri")
        totalBullet = intent.getStringExtra("totalBullet")
        averageSize = intent.getStringExtra("averageSize")

        setInitView()
        setContentView(binding.root)
    }

    override fun onPause() {
        super.onPause()

        overridePendingTransition(0, 0)
    }

    private fun setInitView() {
        Glide
            .with(binding.targetImageView.context)
            .load(Uri.parse(copyUri))
            .into(binding.targetImageView)

        binding.showTotalBullet.text = totalBullet
        binding.showAverageSize.text = averageSize
    }

}

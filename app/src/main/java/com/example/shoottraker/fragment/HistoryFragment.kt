package com.example.shoottraker.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoottraker.activity.ShowHistoryDetailActivity
import com.example.shoottraker.adapter.HistoryAdapter
import com.example.shoottraker.database.HistoryDatabase
import com.example.shoottraker.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private var db: HistoryDatabase? = null

    private val adapter by lazy {
        HistoryAdapter(itemClickListener = {
            val intent = Intent(activity, ShowHistoryDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("copyUri", it.imageUri)
            intent.putExtra("totalBullet", it.totalBullet)
            intent.putExtra("averageSize", it.averageSize)
            startActivity(intent)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = HistoryDatabase.getInstance(activity?.applicationContext)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(layoutInflater.context)

        getHistory()

        return binding.root
    }

    private fun getHistory() {
        Thread {
            val histories = db!!.HistoryDao().getHistory()
            adapter.submitList(histories)
            activity?.runOnUiThread {
                if (histories.isEmpty()) {
                    binding.errorImageView.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                } else {
                    binding.recyclerView.isEnabled = true
                }
            }
        }.start()
    }
}
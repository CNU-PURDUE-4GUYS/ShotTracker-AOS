package com.example.shoottraker.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoottraker.activity.ShowHistoryDetailActivity
import com.example.shoottraker.adapter.HistoryAdapter
import com.example.shoottraker.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        HistoryAdapter(itemClickListener = {
            val intent = Intent(activity, ShowHistoryDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("date", it.id)
            startActivity(intent)
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(layoutInflater.context)

    }
}
package com.example.shoottraker.bottomMenu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoottraker.data.Shot
import com.example.shoottraker.databinding.FragmentHistoryBinding
import com.example.shoottraker.history.DisplayBulletTraceAdapter
import com.example.shoottraker.history.EachHistoryActivity

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        initAdapter()

        return binding.root
    }

    private fun setImages() {
        // todo - connect Image API and set the imageUrl in the arrayOfData
    }

    private fun initAdapter() {
        val adapter = DisplayBulletTraceAdapter(itemClickedListener = {
            val intent = Intent(activity, EachHistoryActivity::class.java)
            startActivity(intent)
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(layoutInflater.context)
        binding.recyclerView.adapter = adapter
    }
}
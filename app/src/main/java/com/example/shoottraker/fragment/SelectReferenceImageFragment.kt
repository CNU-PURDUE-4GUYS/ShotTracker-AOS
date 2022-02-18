package com.example.shoottraker.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoottraker.activity.StartShotActivity
import com.example.shoottraker.adapter.ReferenceAdapter
import com.example.shoottraker.database.ReferenceDatabase
import com.example.shoottraker.databinding.FragmentSelectReferenceBinding


class SelectReferenceImageFragment : Fragment() {
    // Todo 리사이클러뷰가 실행될 때 기본 래퍼런스 이미지 4장 출력하기

    private val binding by lazy {
        FragmentSelectReferenceBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ReferenceAdapter(itemClickListener = {
            val intent = Intent(activity, StartShotActivity::class.java)
            intent.putExtra("refUri", it.refUri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        })
    }

    private var db: ReferenceDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.referenceImageRecyclerView.adapter = adapter
        binding.referenceImageRecyclerView.layoutManager = GridLayoutManager(activity, 2)

        db = ReferenceDatabase.getInstance(activity?.applicationContext)

        getReferenceImage()

        return binding.root
    }

    // Get reference images in the Room DB
    private fun getReferenceImage() {
        try {
            Thread {
                val referenceImages = db!!.ReferenceDao().getAllReferenceImage()
                activity?.runOnUiThread {
                    binding.referenceImageRecyclerView.isVisible = true
                    adapter.submitList(referenceImages)
                }
            }.start()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }
}
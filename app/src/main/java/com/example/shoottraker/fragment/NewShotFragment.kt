package com.example.shoottraker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoottraker.MainActivity
import com.example.shoottraker.database.ReferenceDatabase
import com.example.shoottraker.databinding.FragmentNewShotBinding

class NewShotFragment : Fragment() {
    private val binding by lazy {
        FragmentNewShotBinding.inflate(layoutInflater)
    }

    private var mainActivity: MainActivity? = null
    private var db: ReferenceDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        db = ReferenceDatabase.getInstance(activity?.applicationContext)

        setStartShotButton()
        setTargetImageButton()

        return binding.root
    }

    private fun setTargetImageButton() {
        binding.setCameraButton.setOnClickListener {
            mainActivity?.setAndSaveFragment(SetCameraFragment())
        }
    }

    private fun setStartShotButton() {
        binding.startShotButton.setOnClickListener {
            mainActivity?.setFragment(SelectReferenceImageFragment())
        }
    }
}
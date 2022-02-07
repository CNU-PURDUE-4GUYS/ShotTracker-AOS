package com.example.shoottraker.bottomMenu

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shoottraker.MainActivity
import com.example.shoottraker.R
import com.example.shoottraker.databinding.FragmentNewshotBinding
import com.example.shoottraker.newShot.SetCameraFragment
import com.google.android.material.snackbar.Snackbar

class NewShotFragment : Fragment() {
    private lateinit var binding: FragmentNewshotBinding
    private lateinit var mainActivity: MainActivity
    private var initState = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Todo need to save Fragment state
        if (savedInstanceState != null) {
            initState = savedInstanceState.getBoolean("initState")
        }
        binding = FragmentNewshotBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        setStartShotButton()
        setTargetImageButton()

        return binding.root
    }

    // 카메라 세팅 설정
    private fun setTargetImageButton() {
        binding.setCameraButton.setOnClickListener {
            binding.startShotButton.isEnabled = true
            mainActivity.setAndSaveFragment(SetCameraFragment())
        }
    }

    private fun setStartShotButton() {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("initState", initState)
    }
}
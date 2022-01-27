package com.example.shoottraker.bottomMenu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoottraker.MainActivity
import com.example.shoottraker.databinding.FragmentNewshotBinding

class NewShotFragment : Fragment() {
    private lateinit var binding: FragmentNewshotBinding
    private lateinit var mainActivity: MainActivity

    private var totalSet = 0
    private var bulletPerSet = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewshotBinding.inflate(inflater, container, false)

//        initStartButton()
//        clickStartButton()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = MainActivity()
    }

//    private fun initStartButton() {
//        if (binding.totalSetEditText.text.isNotEmpty() && binding.totalShotEditText.text.isNotEmpty()) {
//            binding.startButton.isEnabled = true
//        }
//    }

//    private fun clickStartButton() {
//        if (binding.totalSetEditText.text.isNotEmpty() && binding.totalShotEditText.text.isNotEmpty()) {
//            binding.startButton.setOnClickListener {
//                totalSet = binding.totalSetEditText.text.toString().toInt()
//                bulletPerSet = binding.totalShotEditText.text.toString().toInt()
//
//                val intent = Intent(mainActivity, InShotActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }
}
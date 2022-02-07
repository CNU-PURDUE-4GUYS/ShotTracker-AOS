package com.example.shoottraker.newShot

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shoottraker.databinding.FragmentSetCameraBinding

class SetCameraFragment : Fragment() {
    private lateinit var binding: FragmentSetCameraBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetCameraBinding.inflate(inflater, container, false)

        setGetPictureButton()
        setTakePictureButton()
        setSavePictureButton()

        return binding.root
    }

    // 갤러리에서 이미지를 가져오는 함수
    private fun setGetPictureButton() {
        binding.getPictureButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_GET_REQUESTED)
        }
    }

    // 카메라에서 이미지를 촬영하는 함수
    private fun setTakePictureButton() {
        binding.takePictureButton.setOnClickListener {
            setCamera()
        }
    }

    private fun setCamera() {
        // Todo 일단 간단한 방법으로 임시로 구현함 -> 촬영한 타겟 이미지 화질 향상을 위해 이후에 수정 필요
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGE_CAPTURE_REQUESTED)
    }

    // 저장 이후 이전 프래그먼트로 돌아가는 함수
    private fun setSavePictureButton() {
        binding.savePictureButton.setOnClickListener {
            val manager = activity?.supportFragmentManager
            manager?.beginTransaction()?.run {
                remove(this@SetCameraFragment)
                commit()
            }
            manager?.popBackStack()
            Toast.makeText(activity, "이미지 설정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                // 카메라에서 촬영하는 경우
                IMAGE_CAPTURE_REQUESTED -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    binding.targetImageView.setImageBitmap(bitmap)
                }

                // 갤러리에서 가져오는 경우
                IMAGE_GET_REQUESTED -> {
                    val uri = data?.data
                    binding.targetImageView.setImageURI(uri)

                }
            }
            binding.savePictureButton.isEnabled = true
        }
    }

    companion object {
        const val IMAGE_CAPTURE_REQUESTED = 30
        const val IMAGE_GET_REQUESTED = 40
    }
}
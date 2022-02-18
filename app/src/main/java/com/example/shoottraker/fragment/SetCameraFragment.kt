package com.example.shoottraker.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoottraker.database.ReferenceDatabase
import com.example.shoottraker.databinding.FragmentSetCameraBinding
import com.example.shoottraker.model.Reference
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.random.Random

class SetCameraFragment : Fragment() {
    private val binding by lazy {
        FragmentSetCameraBinding.inflate(layoutInflater)
    }

    private var db: ReferenceDatabase? = null
    private var bitmap: Bitmap? = null
    private var referenceUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = ReferenceDatabase.getInstance(activity?.applicationContext)

        setGetPictureButton()
        setTakePictureButton()
        setSavePictureButton()

        return binding.root
    }

    // Get Images from gallery
    private fun setGetPictureButton() {
        binding.getPictureButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
        }
    }

    // Get images from camera
    private fun setTakePictureButton() {
        binding.takePictureButton.setOnClickListener {
            setCamera()
        }
    }

    // Intent camera application
    private fun setCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    // Save reference image in the Room DB and return before fragment
    private fun setSavePictureButton() {
        binding.savePictureButton.setOnClickListener {
            // Use thread save in the Room DB
            try {
                val referImage = Reference(id = null, refUri = referenceUri.toString())
                Thread {
                    db!!.ReferenceDao().insertReferenceImage(referImage)
                }.start()

                val manager = activity?.supportFragmentManager
                manager?.beginTransaction()?.run {
                    remove(this@SetCameraFragment)
                    commit()
                }
                manager?.popBackStack()
            } catch (e: Error) {
                Log.d("kodohyeon", "이미지 저장 실패")
            }
        }
    }

    // Get image uri from bitmap
    private fun getImageUri(bitmap: Bitmap): Uri {
        val byteOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
        val path = MediaStore.Images.Media.insertImage(context?.contentResolver, bitmap, Random(Int.MAX_VALUE).toString(), null)
        return Uri.parse(path)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    bitmap = data?.extras?.get("data") as Bitmap
                    referenceUri = getImageUri(bitmap!!)
                    binding.targetImageView.setImageURI(referenceUri)
                }
                REQUEST_IMAGE_GALLERY -> {
                    referenceUri = data!!.data
                    binding.targetImageView.setImageURI(referenceUri)
                }
            }
            binding.savePictureButton.isEnabled = true
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 300
        const val REQUEST_IMAGE_GALLERY = 400
    }
}
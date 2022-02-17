package com.example.shoottraker.activity

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.shoottraker.database.BulletDatabase
import com.example.shoottraker.databinding.ActivityShotBinding
import com.example.shoottraker.model.Bullet
import java.io.ByteArrayOutputStream
import kotlin.random.Random

class StartShotActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityShotBinding.inflate(layoutInflater)
    }

    private val paint: Paint? = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 3F
    }

    private val bullets: ArrayList<Bullet>? = arrayListOf()

    private var db: BulletDatabase? = null
    private var initState: Boolean = true

    private var refUri: String? = null
    private var bitmap: Bitmap? = null
    private var copyBitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private var drawBulletTraces: DrawBulletTraces? = null

    private var totalBullet: Int = 0
    private val radius: Float = 20F

    var pointX: Float = 0F
    var pointY: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = BulletDatabase.getInstance(applicationContext)

        // First image is received image to intent
        refUri = intent.getStringExtra("refUri")

        binding.shotImageView.apply {
            clipToOutline = true
            setImageURI(Uri.parse(refUri))
        }

        binding.shotTextView.text = totalBullet.toString()

        detectSound()
        finishShot()
    }

    // Remove intent animation
    override fun onPause() {
        super.onPause()

        overridePendingTransition(0, 0)
    }

    // Return this activity using back soft key
    // if you want to reset history, remove remark
    override fun onResume() {
        super.onResume()

//        totalBullet = 0
//        binding.shotTextView.text = totalBullet.toString()
//        binding.shotImageView.setImageURI(Uri.parse(refUri))
//
//        // Reset bulletTraces
//        bullets!!.clear()
    }


    // Using DrawBulletTraces class, Draw bulletTraces
    private fun drawBulletTraces() {
        for (bullet in bullets!!.iterator()) {
            // Get x, y point in ArrayList<Bullet>
            pointX = bullet.x
            pointY = bullet.y

            // 좌표가 받아질 때 이전 좌표가 조금 변경된 상태로 전달되기 때문에 반복문을 돌며 배번 새롭게 그린다.
            drawBulletTraces = DrawBulletTraces(binding.shotImageView.context)

            // First image is received image to intent
            if (initState) {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(refUri))
                initState = false
            } else {
                bitmap = copyBitmap
            }

            // To draw bulletTraces, copy bitmap image
            copyBitmap = bitmap!!.copy(Bitmap.Config.ARGB_8888, true)

            // Make canvas using copied bitmap
            canvas = Canvas(copyBitmap!!)

            // Draw bulletTraces and Update UI using runOnUiThread
            drawBulletTraces!!.draw(canvas)
            runOnUiThread {
                binding.shotImageView.setImageBitmap(copyBitmap)
            }
        }
        initState = true
    }

    // If detect the sound, draw bulletTrace on the reference image
    private fun detectSound() {
        binding.detectSoundButton.setOnClickListener {
            // Todo 블루투스 객체에서 값 받아와서 드로잉하기
            pointX += Random.nextInt(10).toFloat()
            pointY += Random.nextInt(10).toFloat()

            Log.d("kodohyeon", "[Receive] $pointX $pointY")

            bullets!!.add(Bullet(null, SET_ID_NUMBER, pointX, pointY))
            binding.shotTextView.text = (++totalBullet).toString()

            drawBulletTraces()
        }
    }

    // If finish the shot, intent showShotDetailActivity
    private fun finishShot() {
        binding.finishButton.setOnClickListener {
            val bytes = ByteArrayOutputStream()
            copyBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, bytes)

            val copyUri = MediaStore.Images.Media.insertImage(contentResolver, copyBitmap, "copyUri", null)

            val intent = Intent(this, ShowShotDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("copyUri", copyUri)
            intent.putExtra("totalBullet", totalBullet.toString())

            startActivity(intent)
        }
    }

    // In order to draw bulletTraces, Declare inner class overriding onDraw
    private inner class DrawBulletTraces(context: Context) : View(context) {
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            canvas?.apply {
                drawCircle(pointX, pointY, radius, paint!!)
            }
        }
    }

    companion object {
        const val SET_ID_NUMBER = 1
    }
}
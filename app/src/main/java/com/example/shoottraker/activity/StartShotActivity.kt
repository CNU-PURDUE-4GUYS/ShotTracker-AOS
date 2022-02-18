package com.example.shoottraker.activity

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.shoottraker.bluetooth.BluetoothConnection
import com.example.shoottraker.databinding.ActivityShotBinding
import com.example.shoottraker.dto.Converters
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Exception
import kotlin.random.Random

class StartShotActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityShotBinding.inflate(layoutInflater)
    }

    // About bluetooth connection
    private val bluetoothAdapter by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }
    private var deviceName: String? = null
    private var deviceMACAddress: String? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var byte: Char? = null
    private var savedByte: String? = null
    private var thread: Thread? = null
    private var initState: Boolean = true
    private var _isConnected: Boolean = false
    private var _isFinished = false

    // About drawing bullet traces
    private var points: Array<Array<Float>> = arrayOf(
        arrayOf()
    )
    private val paint: Paint? = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 3F
    }
    private var refUri: String? = null
    private var bitmap: Bitmap? = null
    private var copyBitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private var drawBulletTraces: DrawBulletTraces? = null
    private val radius: Float = 20F
    private var pointX: Float = 0F
    private var pointY: Float = 0F
    private var totalBullet: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

    // If detect the sound, draw bulletTrace on the reference image
    private fun detectSound() {
        // First, Get information of device using bluetooth
        val devices = bluetoothAdapter.bondedDevices
        devices.forEach { device ->
            deviceName = device.name
            deviceMACAddress = device.address
            bluetoothSocket =
                device.createInsecureRfcommSocketToServiceRecord(
                    java.util.UUID.fromString(
                        BluetoothConnection.UUID
                    )
                )
        }

        // Second, Use thread to detect shot sound
        thread = Thread {
            while (!_isFinished) {
                // Using while, try to connect socket
                while (!_isConnected) {
                    try {
                        bluetoothSocket!!.connect()
                        _isConnected = true
                    } catch (e: Exception) {
                        Log.d("kodohyeon", "연결 실패")
                    }

                }

                // If connect socket success, send target number
                sendTargetNumber()

                // If connect socket success, receive texts until receive "!"
                while (true) {
                    try {
                        byte = bluetoothSocket!!.inputStream!!.read().toChar()
                        if (byte == '!') {
                            Log.d("kodohyeon", "[Receiving] ${savedByte.toString()}")
                            points = Converters().fromStringTo2DArray(savedByte!!)
                            // 변환 과정에서 실제 사이즈보다 1이 크게 변환됨
                            totalBullet = points.size - 1
                            // Draw target traces
                            drawBulletTraces()
                            // Initialize value
                            savedByte = ""
                            break
                        } else {
                            savedByte += byte
                        }
                    } catch (e: IOException) {
                        Log.d("kodohyeon", "연결이 끊겼습니다.")
                    }
                }
                _isConnected = false
            }
        }

        // declared thread start
        thread!!.start()
    }

    // Send target number to RPi
    private fun sendTargetNumber() {
        val _targetNumber = 1
        try {
            val message = _targetNumber.toString()
            val messageToByte = message.toByteArray()
            bluetoothSocket!!.outputStream.write(
                messageToByte
            )
            Log.d("kodohyeon", "${deviceMACAddress}로 ${message}가 보내졌습니다.")
        } catch (e: IOException) {
            Log.d("kodohyeon", "블루투스 연결을 확인해주세요.")
        }
    }

    // Using DrawBulletTraces class, Draw bulletTraces
    private fun drawBulletTraces() {
        for (point in points!!.iterator()) {
            if (point.isEmpty()) continue

            // Get x, y point
            pointX = point[0]
            pointY = point[1]

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

    // In order to draw bulletTraces, Declare inner class overriding onDraw
    private inner class DrawBulletTraces(context: Context) : View(context) {
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            canvas?.apply {
                drawCircle(pointX, pointY, radius, paint!!)
            }
        }
    }

    // If finish the shot, intent showShotDetailActivity
    private fun finishShot() {
        binding.finishButton.setOnClickListener {
            // Disconnect thread
            _isFinished = true
            thread!!.interrupt()

            // Change bitmap to Uri the drew image
            val bytes = ByteArrayOutputStream()
            copyBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, bytes)

            val copyUri =
                MediaStore.Images.Media.insertImage(
                    contentResolver,
                    copyBitmap,
                    Random(100).toString(),
                    null
                )

            val intent = Intent(this, ShowShotDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("refUri", refUri)
            intent.putExtra("copyUri", copyUri.toString())
            intent.putExtra("totalBullet", totalBullet.toString())
            intent.putExtra("points", points)

            startActivity(intent)
            finish()
        }
    }
}
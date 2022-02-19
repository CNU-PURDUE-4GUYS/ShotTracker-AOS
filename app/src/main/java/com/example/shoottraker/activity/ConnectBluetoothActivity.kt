package com.example.shoottraker.activity

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoottraker.databinding.ActivityBluetoothBinding
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class ConnectBluetoothActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityBluetoothBinding.inflate(layoutInflater)
    }

    private val bluetoothAdapter by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    private var deviceName: String? = null
    private var deviceMACAddress: String? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var _isConnected: Boolean = false
    private var connectState: Boolean = true

    private var bitmap: Bitmap? = null

    private var temp: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ConnectDevice().execute()
        ConnectDevice().cancel(true)

        getBluetoothState()
        initBluetooth()
        sendMessage()
        setImage()
        sendImage()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    // Remove intent animation
    override fun onPause() {
        super.onPause()

        overridePendingTransition(0, 0)
    }

    // 블루투스 지원 여부를 확인한다.
    private fun initBluetooth() {
        binding.checkBtButton.setOnClickListener {
            if (bluetoothAdapter == null) {
                showToastMessage(this, "블루투스를 지원하지 않는 기기입니다.")
            } else {
                if (bluetoothAdapter.state == BluetoothAdapter.STATE_ON) {
                    showToastMessage(this, "블루투스가 꺼졌습니다.")
                    bluetoothAdapter.disable() // 블루투스 종료
                } else {
                    val btIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(btIntent, REQUEST_BLUETOOTH_CODE)
                }
            }
        }
    }

    // 브로드캐스트 리시버로 블루투스의 현재 상태를 확인한다.
    private fun getBluetoothState() {
        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(receiver, filter)
    }


    // 블루투스 상태를 받기 위해 브로드캐스트 리시버 구현
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                BluetoothAdapter.STATE_ON -> {
                    binding.checkBtButton.text = "블루투스 끄기"
                }
                BluetoothAdapter.STATE_OFF -> {
                    binding.checkBtButton.text = "블루투스 켜기"
                    bluetoothAdapter.disable()
                }
            }
        }
    }


    // 메세지를 보냄
    private fun sendMessage() {
        binding.sendButton.setOnClickListener {
            try {
                val message = binding.sendEditText.text.toString()
                val messageToByte = message.toByteArray()
                if (message.isEmpty()) {
                    showToastMessage(this, "메세지를 입력해주세요.")
                } else {
                    bluetoothSocket!!.outputStream.write(
                        messageToByte
                    )
                    Log.d("kodohyeon", "${deviceMACAddress}로 ${message}가 보내졌습니다.")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                showToastMessage(this, "파이프라인이 끊겼습니다.")
            }

        }
    }

    private fun setImage() {
        binding.getImageOnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_GALLERY_CODE)
        }
    }

    //    private inner class ConnectDevice : AsyncTask<Void, Void, String>() {
//
//        override fun doInBackground(vararg params: Void?): String? {
//            try {
//                // If device doesn't connect, progress connecting
//                if (bluetoothSocket == null || !_isConnected) {
//                    Log.d("kodohyeon", "[Connecting]")
//                    val devices = bluetoothAdapter.bondedDevices
//                    if (devices.isNotEmpty()) {
//                        devices.forEach { device ->
//                            deviceName = device.name
//                            deviceMACAddress = device.address
//                            bluetoothSocket =
//                                device.createInsecureRfcommSocketToServiceRecord(
//                                    java.util.UUID.fromString(
//                                        BluetoothConnection.UUID
//                                    )
//                                )
//                        }
//                        bluetoothSocket!!.connect()
//                        Log.d("kodohyeon", "[Now Connected] ${deviceName}")
//                    }
//                }
//
//                // If already connected, receive points
//                while (true) {
//                    try {
//                        byte = bluetoothSocket!!.inputStream!!.read().toChar()
//                        if (byte == '!') {
//                            Log.d("kodohyeon", "[Receiving] ${temp.toString()}")
//                            temp = ""
////                        throw IOException()
//                        } else {
//                            temp += byte
//                        }
//                    } catch (e: IOException) {
//                        throw e
//                    }
//                }
//            } catch (e: IOException) {
//                Log.d("kodohyeon", "[Disconnecting]\n${temp.toString()}")
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onProgressUpdate(vararg values: Void?) {
//            super.onProgressUpdate(*values)
//            Log.d("kodohyeon", "[Updating]")
//        }
//
//        // UI를 그린다.
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            Log.d("kodohyeon", "Update UI")
//            // 연결이 끊기면
//            if (!connectState) {
//                showToastMessage(this@BluetoothConnection, "블루투스 연결이 끊겼습니다.")
//                bluetoothSocket?.close()
//            } else {
//                _isConnected = true
//            }
//        }
//    }

    //    private fun detectSound() {
//        binding.detectSoundButton.setOnClickListener {
//            binding.finishButton.isEnabled = true
////            // Todo 블루투스 객체에서 값 받아와서 드로잉하기
////            pointX += Random.nextInt(10).toFloat()
////            pointY += Random.nextInt(10).toFloat()
////
////            Log.d("kodohyeon", "[Receive] $pointX $pointY")
////
////            bullets!!.add(Bullet(null, SET_ID_NUMBER, pointX, pointY))
////            binding.shotTextView.text = (++totalBullet).toString()
//
//            drawBulletTraces()
//        }
//    }

    private fun sendImage() {
        binding.SendImage.setOnClickListener {
            try {
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)

                val byteToArray = byteArrayOutputStream.toByteArray()

                val encoded = Base64.getEncoder().encodeToString(byteToArray)
                Log.d("kodohyeon", "${deviceMACAddress}로 보낼 이미지가 인코딩이 완료됐습니다.")

                bluetoothSocket!!.outputStream.write(encoded.toByteArray())
                bluetoothSocket!!.outputStream.flush()
                Log.d("kodohyeon", "${deviceMACAddress}로 이미지가 보내졌습니다.")

                bluetoothSocket!!.outputStream.write("EOF".toByteArray())
                bluetoothSocket!!.outputStream.flush()
                Log.d("kodohyeon", "${deviceMACAddress}로 EOF가 보내졌습니다.")

//                val size = bitmap!!.rowBytes * bitmap!!.height
//                val buffer = ByteBuffer.allocate(size)
//
//                bitmap!!.copyPixelsToBuffer(buffer)
//
//                val bitmapToByte = buffer.array()
//                bluetoothSocket!!.outputStream.write(bitmapToByte)
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("kodohyeon", "${deviceMACAddress}가 끊겼습니다.")
            }
        }
    }

    private fun receiveMessage() {
        bluetoothSocket?.inputStream
    }

    private fun receiveFiles() {}

    private inner class ConnectDevice : AsyncTask<Void, Void, String>() {
        private var byte: Char? = null

        override fun doInBackground(vararg params: Void?): String? {
            try {
                // If device doesn't connect, progress connecting
                if (bluetoothSocket == null || !_isConnected) {
                    Log.d("kodohyeon", "[Connecting]")
                    val devices = bluetoothAdapter.bondedDevices
                    if (devices.isNotEmpty()) {
                        devices.forEach { device ->
                            deviceName = device.name
                            deviceMACAddress = device.address
                            bluetoothSocket =
                                device.createInsecureRfcommSocketToServiceRecord(
                                    java.util.UUID.fromString(
                                        UUID
                                    )
                                )
                        }
                        bluetoothSocket!!.connect()
                        Log.d("kodohyeon", "[Now Connected] ${deviceName}")
                    }
                }

                // If already connected, receive points
                while (true) {
                    try {
                        byte = bluetoothSocket!!.inputStream!!.read().toChar()
                        if (byte == '!') {
                            Log.d("kodohyeon", "[Receiving] ${temp.toString()}")
                            temp = ""
//                        throw IOException()
                        } else {
                            temp += byte
                        }
                    } catch (e: IOException) {
                        throw e
                    }
                }
            } catch (e: IOException) {
                Log.d("kodohyeon", "[Disconnecting]\n${temp.toString()}")
                e.printStackTrace()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            Log.d("kodohyeon", "[Updating]")
        }

        // UI를 그린다.
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d("kodohyeon", "Update UI")
            // 연결이 끊기면
            if (!connectState) {
                showToastMessage(this@ConnectBluetoothActivity, "블루투스 연결이 끊겼습니다.")
                bluetoothSocket?.close()
            } else {
                _isConnected = true
            }
        }
    }

    private fun showToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_BLUETOOTH_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    showToastMessage(this, "블루투스가 켜졌습니다. 기기 연결을 확인해주세요")
                }
                else -> showToastMessage(this, "기기 연동을 위해 블루투스가 필요합니다.")
            }
        } else if (requestCode == REQUEST_GALLERY_CODE) {
            val uri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }
    }


    companion object {
        const val REQUEST_BLUETOOTH_CODE = 10
        const val REQUEST_GALLERY_CODE = 20
        const val UUID = "00001101-0000-1000-8000-00805f9b34fb"
    }
}
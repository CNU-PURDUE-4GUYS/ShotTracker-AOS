package com.example.shoottraker

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.shoottraker.activity.ConnectBluetoothActivity
import com.example.shoottraker.databinding.ActivityMainBinding
import com.example.shoottraker.fragment.HistoryFragment
import com.example.shoottraker.fragment.NewShotFragment
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermission()
        setBottomNavigation()
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

    fun setAndSaveFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            var permissions = arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, REQUEST_READ_AND_WRITE_EXTERNAL)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
        if (requestCode === REQUEST_READ_AND_WRITE_EXTERNAL) {
            if (grantResults.isNotEmpty()) {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) exitProcess(0)
                }
            }
        }
    }

    private fun setBottomNavigation() {
        binding.bottomNavigation.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.first -> {
                        setFragment(NewShotFragment())
                    }
                    R.id.second -> {
                        setFragment(HistoryFragment())
                    }
                    R.id.third -> {
                        val intent = Intent(this@MainActivity, ConnectBluetoothActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                    }
                }
                return@setOnItemSelectedListener true
            }

            selectedItemId = R.id.first
        }
    }

    companion object {
        const val REQUEST_READ_AND_WRITE_EXTERNAL = 200
    }

}
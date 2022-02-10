package com.example.shoottraker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shoottraker.bluetooth.BluetoothConnection
import com.example.shoottraker.databinding.ActivityMainBinding
import com.example.shoottraker.fragment.HistoryFragment
import com.example.shoottraker.fragment.NewShotFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
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

    private fun initBottomNavigation() {
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
                        val intent = Intent(this@MainActivity, BluetoothConnection::class.java)
                        startActivity(intent)
                    }
                }
                return@setOnItemSelectedListener true
            }

            selectedItemId = R.id.first
        }
    }
}
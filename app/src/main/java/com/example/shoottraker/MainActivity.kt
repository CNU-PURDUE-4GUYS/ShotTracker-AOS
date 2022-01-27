package com.example.shoottraker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shoottraker.databinding.ActivityMainBinding
import com.example.shoottraker.bottomMenu.HistoryFragment
import com.example.shoottraker.bottomMenu.NewShotFragment
import com.example.shoottraker.bottomMenu.UserInfoFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragmentContainer, fragment)
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
//                    R.id.third -> {
//                        setFragment(UserInfoFragment())
//                    }
                }
                return@setOnItemSelectedListener true
            }

            selectedItemId = R.id.first
        }
    }
}
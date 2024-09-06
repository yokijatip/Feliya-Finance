package com.gity.feliyafinance.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.gity.feliyafinance.R
import com.gity.feliyafinance.databinding.ActivityMainBinding
import com.gity.feliyafinance.ui.home.HomeFragment
import com.gity.feliyafinance.ui.report.ReportFragment
import com.gity.feliyafinance.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        replaceFragment(HomeFragment())

        binding.apply {
            chipNavigationBar.setItemSelected(R.id.home, true)
            chipNavigationBar.setOnItemSelectedListener {
                when (it) {
                    R.id.home -> replaceFragment(HomeFragment())
                    R.id.report -> replaceFragment(ReportFragment())
                    R.id.settings -> replaceFragment(SettingsFragment())

                    else -> {

                    }
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
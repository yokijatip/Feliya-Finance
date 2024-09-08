package com.gity.feliyafinance.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.gity.feliyafinance.R
import com.gity.feliyafinance.databinding.ActivitySplashScreenBinding
import com.gity.feliyafinance.ui.auth.AuthActivity
import com.gity.feliyafinance.ui.main.MainActivity
import com.gity.feliyafinance.utils.DataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dataStoreManager = DataStoreManager(this)

        lifecycleScope.launch {
            delay(2000)
            checkLoginStatus()
            finish()
        }


    }

    private suspend fun checkLoginStatus() {
        dataStoreManager.emialFlow.collect { email ->
            if (email.isEmpty()) {
                navigateToAuth()
            } else {
                navigateToMain()
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        val optionsCompat: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@SplashScreenActivity,
                binding.splashImage,
                "logo"
            )
        startActivity(intent, optionsCompat.toBundle())
        finish()
    }

    private fun navigateToAuth() {
        val intent = Intent(this@SplashScreenActivity, AuthActivity::class.java)
        val optionsCompat: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@SplashScreenActivity,
                binding.splashImage,
                "logo"
            )
        startActivity(intent, optionsCompat.toBundle())
        finish()
    }


}
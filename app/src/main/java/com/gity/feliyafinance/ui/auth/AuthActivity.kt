package com.gity.feliyafinance.ui.auth

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.gity.feliyafinance.R
import com.gity.feliyafinance.databinding.ActivityAuthBinding
import com.gity.feliyafinance.ui.auth.login.LoginFragment
import com.gity.feliyafinance.ui.auth.register.RegisterFragment

@Suppress("DEPRECATION")
class AuthActivity : AppCompatActivity() {
    private var binding: ActivityAuthBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAuthBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        replaceFragment(LoginFragment())

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                val fragment = supportFragmentManager.findFragmentById(
//                    R.id.frame_layout_auth
//                )
//
//                if (fragment is RegisterFragment) {
//                    replaceFragment(LoginFragment())
//                } else {
//                    isEnabled = false
//                    onBackPressed()
//                }
//            }
//
//        })
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_auth, fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
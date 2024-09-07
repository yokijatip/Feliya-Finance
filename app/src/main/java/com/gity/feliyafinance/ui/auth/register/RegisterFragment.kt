package com.gity.feliyafinance.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.gity.feliyafinance.R
import com.gity.feliyafinance.databinding.FragmentRegisterBinding
import com.gity.feliyafinance.ui.auth.AuthActivity
import com.gity.feliyafinance.ui.auth.login.LoginFragment
import com.gity.feliyafinance.ui.main.MainActivity

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.apply {
            btnBack.setOnClickListener {
                (activity as? AuthActivity)?.replaceFragment(LoginFragment())
            }
            logo.setOnClickListener {
                edtEmail.setText("yokijati@gmail.com")
                edtPassword.setText("12345678")
            }
            btnRegister.setOnClickListener {
                if (validateUserInput()) {
                    Log.i("AuthMessage", "Register Successful")
                    navigateToMain()
                } else {
                    Log.i("AuthMessage", "Register Failed")
                }
            }
        }

        return binding.root
    }

    private fun navigateToMain() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        val optionsCompat: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                binding.logo,
                "logo"
            )
        startActivity(intent, optionsCompat.toBundle())
        requireActivity().finish()
    }

    private fun validateUserInput(): Boolean {
        val thisContext = requireActivity()
        binding.apply {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty()) {
                edtEmail.error = thisContext.getString(R.string.error_email_empty)
                edtEmail.requestFocus()
                return false
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.error = thisContext.getString(R.string.error_email_invalid)
                binding.edtEmail.requestFocus()
                return false
            }

            if (password.isEmpty()) {
                binding.edtPassword.error = thisContext.getString(R.string.error_password_empty)
                binding.edtPassword.requestFocus()
                return false
            }

            if (password.length < 8) {
                binding.edtPassword.error = thisContext.getString(R.string.error_password_length)
                binding.edtPassword.requestFocus()
                return false
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
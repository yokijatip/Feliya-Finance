package com.gity.feliyafinance.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gity.feliyafinance.R
import com.gity.feliyafinance.data.Repository
import com.gity.feliyafinance.data.local.database.FeliyaDatabase
import com.gity.feliyafinance.databinding.FragmentLoginBinding
import com.gity.feliyafinance.ui.auth.AuthActivity
import com.gity.feliyafinance.ui.auth.AuthViewModel
import com.gity.feliyafinance.ui.auth.register.RegisterFragment
import com.gity.feliyafinance.ui.main.MainActivity
import com.gity.feliyafinance.utils.DataStoreManager
import com.gity.feliyafinance.utils.ViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        dataStoreManager = DataStoreManager(requireContext())

        val userDao = FeliyaDatabase.getDatabase(requireContext()).userDao()
        val repository = Repository(userDao)
        val factory = ViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        binding.apply {
            btnToRegister.setOnClickListener {
                (activity as? AuthActivity)?.replaceFragment(RegisterFragment())
            }
            logo.setOnClickListener {
                edtEmail.setText("yokijati@gmail.com")
                edtPassword.setText("12345678")
            }
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()
                if (validateUserInput(email, password)) {
                    authViewModel.login(email, password)
                } else {
                    Log.i("AuthMessage", "Login Failed")
                }
            }
        }

        authViewModel.loginResult.observe(viewLifecycleOwner) {
            if (it != null) {
                lifecycleScope.launch {
                    val emailWantToSave: String? = it.email
                    if (emailWantToSave != null) {
                        Log.i("AuthMessage", "Email Saved in Data Store Manager")
                        saveEmail(emailWantToSave)
                    } else {
                        Log.i("AuthMessage", "Email is Empty")
                    }
                }
                navigateToMain()
            } else {
                Log.i("AuthMessage", "Error Login")
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

    private fun validateUserInput(email: String, password: String): Boolean {
        val thisContext = requireActivity()
        binding.apply {
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

    private suspend fun saveEmail(email: String) {
        dataStoreManager.saveEmail(email)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
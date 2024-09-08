package com.gity.feliyafinance.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gity.feliyafinance.R
import com.gity.feliyafinance.data.Repository
import com.gity.feliyafinance.data.local.database.FeliyaDatabase
import com.gity.feliyafinance.data.local.model.User
import com.gity.feliyafinance.databinding.FragmentRegisterBinding
import com.gity.feliyafinance.ui.auth.AuthActivity
import com.gity.feliyafinance.ui.auth.AuthViewModel
import com.gity.feliyafinance.ui.auth.login.LoginFragment
import com.gity.feliyafinance.ui.main.MainActivity
import com.gity.feliyafinance.utils.DataStoreManager
import com.gity.feliyafinance.utils.ViewModelFactory
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        dataStoreManager = DataStoreManager(requireContext())

        val userDao = FeliyaDatabase.getDatabase(requireContext()).userDao()
        val repository = Repository(userDao)
        val factory = ViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        authViewModel.emailExists.observe(viewLifecycleOwner) {
            if (it) {
//                Munculkan error jika email sudah ada
                val emailAlreadyExistInformation =
                    requireContext().getString(R.string.email_already_exists)
                Toast.makeText(requireContext(), emailAlreadyExistInformation, Toast.LENGTH_SHORT)
                    .show()
            } else {
                registerUser()
            }
        }

        binding.apply {
            btnBack.setOnClickListener {
                (activity as? AuthActivity)?.replaceFragment(LoginFragment())
            }
            logo.setOnClickListener {
                edtEmail.setText("yokijati@gmail.com")
                edtPassword.setText("12345678")
            }
            btnRegister.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()
                if (validateUserInput(email, password)) {
                    authViewModel.checkEmailExists(email)
                }
            }
        }

        authViewModel.registerResult.observe(viewLifecycleOwner) { user ->
            if (user) {
                Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    saveEmail(binding.edtEmail.text.toString().trim())
                }
                navigateToMain()
            } else {
                Log.i("AuthMessage", "Error Register")
            }
        }


        return binding.root
    }

    private fun registerUser() {
        binding.apply {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val role = "User"
            val user = User(email = email, password = password, role = role)

            authViewModel.register(user)
        }
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
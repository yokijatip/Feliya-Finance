package com.gity.feliyafinance.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gity.feliyafinance.R
import com.gity.feliyafinance.databinding.FragmentSettingsBinding
import com.gity.feliyafinance.helper.CommonUtils
import com.gity.feliyafinance.ui.auth.AuthActivity
import com.gity.feliyafinance.utils.DataStoreManager
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        dataStoreManager = DataStoreManager(requireContext())

        binding.apply {
            btnLogout.setOnClickListener {
                CommonUtils.showConfirmationDialog(
                    requireContext(),
                    requireContext().getString(R.string.logout),
                    requireContext().getString(R.string.setting_logout_confirmation),
                    onConfirm = {
                        lifecycleScope.launch { performLogout() }
                    }
                )

            }
        }

        return binding.root

    }

    private suspend fun performLogout() {
        dataStoreManager.clearEmail()
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
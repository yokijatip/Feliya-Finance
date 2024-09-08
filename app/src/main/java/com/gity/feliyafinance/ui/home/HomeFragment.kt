package com.gity.feliyafinance.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gity.feliyafinance.databinding.FragmentHomeBinding
import com.gity.feliyafinance.helper.CommonUtils
import java.text.NumberFormat
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {
            val amount = 340000000
            val amountToWords = CommonUtils.convertNumberToWords(amount) + " rupiah"
            tvTotalBalance.text = CommonUtils.formatToCurrency(340000000)
            tvTotalBalanceInWords.text = amountToWords
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
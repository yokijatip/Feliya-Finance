package com.gity.feliyafinance.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.gity.feliyafinance.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonUtils {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun showConfirmationDialog(context: Context, title: String, message: String, onConfirm: () -> Unit ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_confirmation)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialog.findViewById<TextView>(R.id.dialog_confirmation_title)
        val tvMessage = dialog.findViewById<TextView>(R.id.dialog_confirmation_message)
        val btnConfirm = dialog.findViewById<TextView>(R.id.btn_confirm)
        val btnCancel = dialog.findViewById<TextView>(R.id.btn_cancel)

        tvTitle.text = title
        tvMessage.text = message

        btnConfirm.setOnClickListener {
            onConfirm()
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun formatToCurrency(amount: Int): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        val formatted = numberFormat.format(amount)

        // Menambahkan spasi antara simbol mata uang dan angka
        return formatted.replace("Rp", "Rp ")
    }

    fun convertNumberToWords(number: Int): String {
        val words = arrayOf(
            "", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas"
        )

        return when {
            number < 12 -> words[number]
            number < 20 -> "${convertNumberToWords(number - 10)} belas"
            number < 100 -> "${convertNumberToWords(number / 10)} puluh ${convertNumberToWords(number % 10)}"
            number < 200 -> "seratus ${convertNumberToWords(number - 100)}"
            number < 1000 -> "${convertNumberToWords(number / 100)} ratus ${convertNumberToWords(number % 100)}"
            number < 2000 -> "seribu ${convertNumberToWords(number - 1000)}"
            number < 1000000 -> "${convertNumberToWords(number / 1000)} ribu ${convertNumberToWords(number % 1000)}"
            number < 1000000000 -> "${convertNumberToWords(number / 1000000)} juta ${convertNumberToWords(number % 1000000)}"
            else -> "Angka terlalu besar"
        }.trim()
    }

}
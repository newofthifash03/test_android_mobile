package com.example.andorid_mobile.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.andorid_mobile.databinding.ActivityFirstScreenBinding

class FirstScreen : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        // Tombol CHECK untuk cek palindrome
        binding.btnCheck.setOnClickListener {
            val inputText = binding.inputPalindrome.text.toString().trim()
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter text to check palindrome", Toast.LENGTH_SHORT).show()
            } else {
                if (isPalindrome(inputText)) {
                    Toast.makeText(this, "It's a Palindrome", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Not a Palindrome", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Tombol NEXT untuk lanjut ke SecondScreen
        binding.btnNext.setOnClickListener {
            val name = binding.inputName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@FirstScreen, SecondScreen::class.java).apply {
                    putExtra("name", name)
                    // Anda bisa sesuaikan data lain jika perlu
                }
                startActivity(intent)
            }
        }
    }

    // Fungsi pembantu mengecek palindrome (case insensitive dan ignore spasi)
    private fun isPalindrome(text: String): Boolean {
        val cleaned = text.replace("\\s+".toRegex(), "").lowercase()
        return cleaned == cleaned.reversed()
    }
}

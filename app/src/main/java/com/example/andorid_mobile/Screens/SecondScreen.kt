package com.example.andorid_mobile.Screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.example.andorid_mobile.databinding.ActivitySecondScreenBinding

class SecondScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val name = intent.getStringExtra("name").orEmpty()
        val username = intent.getStringExtra("username").orEmpty()

        // Atur tampilan nama
        if (name.isNotEmpty()) {
            binding.tvName.text = name
        } else {
            binding.tvName.text = "John Doe"
        }

        // Atur tampilan username dan visibilitas textview
        if (username.isNotEmpty()) {
            binding.tvUsername.visibility = View.INVISIBLE
            binding.tvSelected.visibility = View.VISIBLE
            binding.tvSelected.text = username
        } else {
            binding.tvUsername.visibility = View.VISIBLE
            binding.tvSelected.visibility = View.INVISIBLE
        }

        setupListeners()
    }

    private fun setupListeners() {
        // Tombol back ke FirstScreen
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, FirstScreen::class.java)
            startActivity(intent)
            finish() // optional: menutup activity saat ini supaya tidak bisa back kembali
        }

        // Tombol pilih user ke ThirdScreen
        binding.btnChoose.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            intent.putExtra("name", binding.tvName.text.toString())
            startActivity(intent)
        }
    }
}

package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OnlineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online)
        supportActionBar?.hide()
    }
}
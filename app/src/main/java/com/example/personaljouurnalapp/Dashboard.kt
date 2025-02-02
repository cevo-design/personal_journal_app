package com.example.personaljouurnalapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.personaljouurnalapp.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {

  private lateinit var binding: ActivityDashboardBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityDashboardBinding.inflate(layoutInflater)
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    binding.makeJournalEntryBtn.setOnClickListener {
      val intent = Intent(this, JournalEntry::class.java)
      startActivity(intent)
    }

    binding.viewJournalEntriesBtn.setOnClickListener {
      val intent = Intent(this, JournalsList::class.java)
      startActivity(intent)
    }
  }
}

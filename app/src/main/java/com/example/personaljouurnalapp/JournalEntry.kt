package com.example.personaljouurnalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.personaljouurnalapp.databinding.ActivityJournalEntryBinding

class JournalEntry : AppCompatActivity() {

  private lateinit var binding: ActivityJournalEntryBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityJournalEntryBinding.inflate(layoutInflater)
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    // Set up the spinner
    val spinnerAdapter = ArrayAdapter.createFromResource(
      this,
      R.array.journal_categories,
      android.R.layout.simple_spinner_item
    )
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    binding.categorySpinner.adapter = spinnerAdapter

    binding.addJournalBtn.setOnClickListener {
      val title = binding.journalTitleEditText.text.toString()
      val content = binding.journalContentEditText.text.toString()
      val category = binding.categorySpinner.selectedItem.toString()

      if (title.isNotBlank() && content.isNotBlank()) {
        addJournalEntry(title, content, category)
        binding.journalTitleEditText.text.clear()
        binding.journalContentEditText.text.clear()
      }
    }
  }

  private fun addJournalEntry(title: String, content: String, category: String) {
    val entryView = LayoutInflater.from(this).inflate(R.layout.journal_entry_card, binding.journalEntriesContainer, false)
    val titleTextView: TextView = entryView.findViewById(R.id.titleTextView)
    val contentTextView: TextView = entryView.findViewById(R.id.contentTextView)

    titleTextView.text = title
    contentTextView.text = content

    binding.journalEntriesContainer.addView(entryView)
  }
}

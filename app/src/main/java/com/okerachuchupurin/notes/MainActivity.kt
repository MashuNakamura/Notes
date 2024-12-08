package com.okerachuchupurin.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.okerachuchupurin.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Konteks panggil Database Helper ke Activity ini
        db = NotesDatabaseHelper(this)

        // Refresh Tampilan
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        refreshNotes()

        // Kalau mau add Note, akan ada Intent ke arah AddNoteActivity
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    // Isi function Refresh
    fun refreshNotes() {
        val notes = db.getAllNotes()
        notesAdapter = NotesAdapter(notes, this)
        binding.notesRecyclerView.adapter = notesAdapter

        if (notes.isEmpty()) {
            binding.notesRecyclerView.visibility = View.GONE
            binding.emptyTextView.visibility = View.VISIBLE

        } else {
            binding.notesRecyclerView.visibility = View.VISIBLE
            binding.emptyTextView.visibility = View.GONE
        }
    }

    // Memanggil Function Refresh dan Akan selalu Refresh saat aplikasi masih digunakan
    override fun onResume() {
        super.onResume()
        refreshNotes()
    }
}
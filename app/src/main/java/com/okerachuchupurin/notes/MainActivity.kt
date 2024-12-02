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

        db = NotesDatabaseHelper(this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        refreshNotes()

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshNotes() // Memperbarui daftar catatan saat kembali ke layar utama
    }

    fun refreshNotes() {
        val notes = db.getAllNotes()
        notesAdapter = NotesAdapter(notes, this)
        binding.notesRecyclerView.adapter = notesAdapter

        // Atur visibilitas berdasarkan kondisi daftar catatan
        if (notes.isEmpty()) {
            binding.notesRecyclerView.visibility = View.GONE
            binding.emptyTextView.visibility = View.VISIBLE
        } else {
            binding.notesRecyclerView.visibility = View.VISIBLE
            binding.emptyTextView.visibility = View.GONE
        }
    }
}
package com.okerachuchupurin.notes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.okerachuchupurin.notes.databinding.ActivityUpdateBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        // Pesan kalau Note_ID gagal ditemukan, maka akan diarahkan ke nilai default yaitu
        // -1 untuk mencegah crash
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            Toast.makeText(this, "Note ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Ini untuk mengambil value dari title dan content
        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        // Push apabila ada perubahan pada notepad
        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updatedNote = Note(noteId, newTitle, newContent)
            db.updateNote(updatedNote)
            finish()
            // Pesan apabila sudah di Update
            Toast.makeText(this, "Berhasil di Update", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.okerachuchupurin.notes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.okerachuchupurin.notes.databinding.ActivityUpdateBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NotesDatabaseHelper // Include menghubungkan NotesDatabaseHelper
    private var noteId: Int = -1 // Nilai Default noteId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deklarasi db dari NotesDatabaseHelper Activity
        db = NotesDatabaseHelper(this)

        // Mengambil note_id dan kalau note_id gagal ditemukan, maka akan diarahkan ke nilai default yaitu -1 untuk mencegah crash
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            Toast.makeText(this, "Note ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Ambil data note dari database (data yang udah ada pada note) berdasarkan id
        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        // Penyimpanan Variable yang baru
        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()

            // Pengecekan apakah ada perubahan atau tidak
            if (newTitle == note.title && newContent == note.content) {
                // Jika tidak ada perubahan, maka tidak Update dan kirim pesan "Tidak ada perubahan"
                Toast.makeText(this, "Tidak ada perubahan", Toast.LENGTH_SHORT).show()
                finish()

            } else {
                // Ketika ada perubahan, maka Update dan kirim pesan "Perubahan telah Disimpan"
                val updatedNote = Note(noteId, newTitle, newContent)
                db.updateNote(updatedNote)
                Toast.makeText(this, "Perubahan telah Disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
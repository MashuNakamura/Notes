package com.okerachuchupurin.notes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.okerachuchupurin.notes.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddNoteBinding
    private lateinit var db : NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0, title, content)
            if (title.isEmpty()){
                Toast.makeText(this, "Tolong Isi Title-nya !", Toast.LENGTH_SHORT).show()
            }
            else if (content.isEmpty()){
                Toast.makeText(this, "Tolong Isi Deskripsi-nya !", Toast.LENGTH_SHORT).show()
            }
            else {
                db.insertNote(note)
                finish()
                Toast.makeText(this, "Note Tersimpan !", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
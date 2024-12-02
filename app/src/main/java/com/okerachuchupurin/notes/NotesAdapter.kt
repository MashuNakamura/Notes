package com.okerachuchupurin.notes

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val notes: List<Note>, private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val shareButton: ImageView = itemView.findViewById(R.id.shareButton) // Tombol share
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        // Tombol Update
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Tombol Delete
        holder.deleteButton.setOnClickListener {
            val confirmPopout = AlertDialog.Builder(context)
            confirmPopout.setTitle("Konfirmasi Penghapusan")
            confirmPopout.setMessage("Apakah Anda yakin ingin menghapus catatan ini?")
            confirmPopout.setPositiveButton("Ya") { dialog, _ ->
                db.deleteNote(note.id) // Hapus catatan dari database

                // Jika context adalah MainActivity, panggil refreshNotes()
                if (context is MainActivity) {
                    context.refreshNotes()
                }

                Toast.makeText(
                    holder.itemView.context,
                    "Catatan berhasil dihapus",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
            confirmPopout.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = confirmPopout.create()
            dialog.show()
        }

        // Tombol Share
        holder.shareButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Judul: ${note.title}\nIsi: ${note.content}")
                type = "text/plain"
            }
            holder.itemView.context.startActivity(
                Intent.createChooser(shareIntent, "Bagikan Notes ${note.title}")
            )
        }
    }
}

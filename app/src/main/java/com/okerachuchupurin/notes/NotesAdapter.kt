package com.okerachuchupurin.notes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog

class NotesAdapter(private var notes: List<Note>, private val context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        // Edit note
        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Confirm Code
        holder.deleteButton.setOnClickListener {
            // Create Dialog
            val confirm_popout = AlertDialog.Builder(context)
            confirm_popout.setTitle("Konfirmasi Penghapusan")
            confirm_popout.setMessage("Apakah Anda yakin ingin menghapus catatan ini?")

            // Set tombol "Ya" untuk melanjutkan penghapusan
            confirm_popout.setPositiveButton("Ya") { dialog, _ ->
                db.deleteNote(note.id)  // Hapus catatan dari database
                refreshData(db.getAllNotes())  // Perbarui daftar catatan
                Toast.makeText(holder.itemView.context, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
                dialog.dismiss()  // Tutup dialog
            }

            // Set tombol "Tidak" untuk membatalkan penghapusan
            confirm_popout.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()  // Tutup dialog tanpa penghapusan
            }

            // Tampilkan dialog
            val dialog = confirm_popout.create()

            // Pastikan warna tombol mengikuti tema
            dialog.setOnShowListener {
                // Mengambil warna dari atribut tema
                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

                val textColor = if (context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK ==
                    android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    // Jika mode gelap aktif, gunakan warna terang
                    context.getColor(android.R.color.white)
                } else {
                    // Jika mode terang aktif, gunakan warna gelap
                    context.getColor(android.R.color.black)
                }

                // Atur warna tombol
                positiveButton.setTextColor(textColor)
                negativeButton.setTextColor(textColor)
            }
            dialog.show() // Tampilkan dialog konfirmasi
        }
    }

    // Metode untuk memperbarui data
    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged() // Update RecyclerView
    }
}

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
import android.widget.LinearLayout

class NotesAdapter(private val notes: List<Note>, private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    // Deklarasi Class ItemView (Tite, Content, Edit Button, Delete Button, Share Button
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val shareButton: ImageView = itemView.findViewById(R.id.shareButton)
        val cardView: LinearLayout = itemView.findViewById(R.id.cardNotesView)
    }

    // View dari Notes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return NoteViewHolder(view)
    }

    // Mengambil Total Notes
    override fun getItemCount(): Int = notes.size

    // Letak posisi indeks
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        // Event Listener saat Klik Card View
        holder.cardView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Event Listener saat Klik Title
        holder.titleTextView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Event Listener saat Klik Content
        holder.contentTextView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id" ,note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Event Listener Tombol Update
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Event Listener Tombol Delete
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

                // Kalau beneran dihapus akan memunculkan Teks
                Toast.makeText(holder.itemView.context,"Catatan telah dihapus", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            // Popout untuk Konfirmasi
            confirmPopout.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }

            //  Membuat Popout
            val dialog = confirmPopout.create()
            dialog.setOnShowListener {

                // Ambil tombol positif dan negatif (Ya dan Tidak)
                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

                // Deteksi Dark Theme pada user Pengguna
                val nightMode = context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
                val textColor = if (nightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    context.getColor(android.R.color.white) // Warna teks putih untuk mode malam
                } else {
                    context.getColor(android.R.color.black) // Warna teks hitam untuk mode terang
                }

                // Setel warna teks tombol
                positiveButton.setTextColor(textColor)
                negativeButton.setTextColor(textColor)
            }
            dialog.show()
        }

        // Event Listener Tombol Share
        holder.shareButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Judul : ${note.title}\nDeskripsi : ${note.content}")
                type = "text/plain"
            }
            // Isi saat melakukan Share
            holder.itemView.context.startActivity(
                Intent.createChooser(shareIntent, "Bagikan Notes : ${note.title}")
            )
        }
    }
}
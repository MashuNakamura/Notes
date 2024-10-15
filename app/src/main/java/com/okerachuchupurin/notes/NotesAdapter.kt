//package com.okerachuchupurin.notes
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class NotesAdapter(private var notes: List<Note>, context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>{
//
//        class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
//            val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
//        }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
//        return NoteViewHolder(view)
//    }
//
//    override fun getItemCount(): Int = notes.size
//
//    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
//        val note = notes[position]
//        holder.titleTextView.text = note.title
//        holder.contentTextView.text = note.content
//    }
//
//}

package com.okerachuchupurin.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Data class untuk Note
data class NoteData(val title: String, val content: String)

// Adapter untuk RecyclerView
class NotesAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    // ViewHolder untuk item Note
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Inisialisasi TextView di dalam constructor
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
    }

    // Membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return NoteViewHolder(view)
    }

    // Mengembalikan jumlah item dalam daftar
    override fun getItemCount(): Int = notes.size

    // Mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
    }
}
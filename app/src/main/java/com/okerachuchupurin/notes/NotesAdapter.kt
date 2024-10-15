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

import android.content.Context // Changed
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, private val context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
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
    }

    // Metode untuk memperbarui data
    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged() // Memberitahu RecyclerView untuk memperbarui tampilan
    }
}
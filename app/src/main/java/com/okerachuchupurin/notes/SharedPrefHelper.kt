package com.okerachuchupurin.notes

import android.content.Context
import android.content.SharedPreferences

object SharedPrefHelper {

    // Value Private Nama dan Last Note ID
    public const val PREF_NAME = "notes_pref"
    private const val LAST_NOTE_ID = "last_note_id"

    // Function untuk menyimpan LastID
    fun saveLastNoteId(context: Context, noteId: Int) {
        val editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit() // membuka editor SharePref
        editor.putInt(LAST_NOTE_ID, noteId) // Return id ke LAST_NOTE_ID
        editor.apply()
    }

    // Mengambil Last Note ID
    fun getLastNoteId(context: Context): Int {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(LAST_NOTE_ID, -1)
    }
}
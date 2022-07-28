package com.example.notesapp.repositories

import com.example.notesapp.data.Note
import com.example.notesapp.data.db.NotesDatabase

class NotesRepo(
    notesDatabase: NotesDatabase
) {

    val notesDao = notesDatabase.noteDao

    suspend fun upsertNote(note:Note) = notesDao.upsertNote(note)

    suspend fun deleteNote(note: Note) = notesDao.deleteNote(note)

    fun getNotes() = notesDao.selectNotes()

    fun searchNotes(searchQuery: String) = notesDao.searchInNotesTitle(searchQuery)

    suspend fun deleteAllNotes() = notesDao.deleteAllNotes()
}
package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repositories.NotesRepo

class NotesViewModelProviderFactory(
    private val notesRepo: NotesRepo
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepo) as T
    }
}
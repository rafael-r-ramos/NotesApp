package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.adapters.NotesAdapter
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.viewmodel.NotesViewModel
import kotlinx.coroutines.flow.collect


class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.notes.collect { notesList->
                notesAdapter.differ.submitList(notesList)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchNotes.collect { searchedNotes ->
                notesAdapter.differ.submitList(searchedNotes)
            }
        }

        binding.edSearch.addTextChangedListener {
            viewModel.searchNotes(it.toString().trim())
        }

        binding.btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_newNoteFragment)
        }

        notesAdapter.onCLick = {note ->
            val bundle = Bundle().apply {
                putParcelable("note",note)
            }

            findNavController().navigate(R.id.action_notesFragment_to_newNoteFragment,bundle)

        }
    }

    private fun setupRecyclerView() {
        notesAdapter = NotesAdapter()
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesAdapter
        }
    }


}
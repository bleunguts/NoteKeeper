package com.montogo.notekeeper.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.montogo.notekeeper.*
import com.montogo.notekeeper.databinding.FragmentNotesBinding

enum class NotesFragmentType {
    Notes,
    RecentNotes
}

class NotesFragment : Fragment(), NoteRecyclerAdapter.OnNoteSelectedListener {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)
    }

    private val noteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(requireContext(), DataManager.loadNotes())
        adapter.setOnSelectedListener(this)
        adapter
    }

    private val recentlyViewedNoteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(requireContext(), viewModel.recentlyViewedNotes)
        adapter.setOnSelectedListener(this)
        adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(viewModel.isNewlyCreated && savedInstanceState != null)
            viewModel.restoreState(savedInstanceState)
        viewModel.isNewlyCreated = false

        val listItems = root.findViewById<RecyclerView>(R.id.listItems)
        listItems.layoutManager = LinearLayoutManager(requireContext())
        listItems.adapter = when (requireArguments().get("type") as NotesFragmentType) {
            NotesFragmentType.Notes -> noteRecyclerAdapter
            NotesFragmentType.RecentNotes -> recentlyViewedNoteRecyclerAdapter
        }

        val fab = root.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            println("fab on click called.")
            val activityIntent = Intent(requireContext(), NoteActivity::class.java)
            startActivity(activityIntent)
            println("fab on called completed.")
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        val listItems = binding.root.findViewById<RecyclerView>(R.id.listItems)
        listItems.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNoteSelected(note: NoteInfo) {
        viewModel.addToRecentlyViewedNotes(note)
        println("Note added.")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (outState != null && viewModel != null)
            viewModel.saveState(outState)
    }
}
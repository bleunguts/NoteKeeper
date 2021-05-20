package com.montogo.notekeeper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.montogo.notekeeper.CourseRecyclerAdapter
import com.montogo.notekeeper.DataManager
import com.montogo.notekeeper.NoteRecyclerAdapter
import com.montogo.notekeeper.R
import com.montogo.notekeeper.databinding.FragmentCoursesBinding

class CoursesFragment : Fragment() {
    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listItems = root.findViewById<RecyclerView>(R.id.listItems)
        val context = requireContext()
        listItems.layoutManager = GridLayoutManager(context, 2)
        listItems.adapter = CourseRecyclerAdapter(context, DataManager.courses.values.toList())

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
}
package com.montogo.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.montogo.notekeeper.databinding.ActivityNoteListBinding

class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }

        val listNotes = findViewById<ListView>(R.id.listNotes)
        listNotes.adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            DataManager.notes
        )

        listNotes.setOnItemClickListener { parent, view, position, id ->
            val activityIntent = Intent(this, MainActivity::class.java)
            activityIntent.putExtra(NOTE_POSITION, position)
            startActivity(activityIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        val listNotes = findViewById<ListView>(R.id.listNotes)
        (listNotes.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()
    }
}
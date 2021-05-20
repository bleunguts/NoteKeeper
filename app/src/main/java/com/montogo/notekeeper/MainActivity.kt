package com.montogo.notekeeper

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.montogo.notekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val adapterCourses = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.spinnerCourses).adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
                        intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
            displayNote()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        findViewById<EditText>(R.id.textNoteTitle).setText(note.title)
        findViewById<EditText>(R.id.textNoteText).setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        findViewById<Spinner>(R.id.spinnerCourses).setSelection(coursePosition)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = findViewById<EditText>(R.id.textNoteTitle).text.toString()
        note.text = findViewById<EditText>(R.id.textNoteText).text.toString()
        note.course = findViewById<Spinner>(R.id.spinnerCourses).selectedItem as CourseInfo
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            R.id.action_back -> {
                moveBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex) {
            disableMenuItem(menu, R.id.action_next)
        }
        if (notePosition <= 0) {
            disableMenuItem(menu, R.id.action_back)
        }
        if (notePosition > 0 && notePosition < DataManager.notes.lastIndex) {
            enableMenuItem(menu, R.id.action_next, getDrawable(R.drawable.ic_baseline_arrow_forward_24))
            enableMenuItem(menu, R.id.action_back, getDrawable(R.drawable.ic_baseline_arrow_back_24))
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun enableMenuItem(menu: Menu?, id: Int, drawableIcon: Drawable?) {
        val menuItem = menu?.findItem(id)
        if (menuItem != null) {
            menuItem.setIcon(drawableIcon)
            menuItem.isEnabled = true
        }
    }

    private fun disableMenuItem(menu: Menu?, id: Int) {
        val menuItem = menu?.findItem(id)
        if (menuItem != null) {
            menuItem.setIcon(getDrawable(R.drawable.ic_baseline_block_24))
            menuItem.isEnabled = false
        }
    }

    private fun moveBack() {
        --notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }
}
package com.montogo.notekeeper.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.montogo.notekeeper.DataManager
import com.montogo.notekeeper.NoteInfo

class NotesViewModel : ViewModel() {
    var isNewlyCreated: Boolean = true
    private val maxRecentlyViewedNotes = 5
    val recentlyViewedNotes = ArrayList<NoteInfo>(maxRecentlyViewedNotes)
    val recentlyViewedNotesName = "com.montogo.notekeeper.ui.NotesViewModel.recentlyViewedNotes"

    fun addToRecentlyViewedNotes(note: NoteInfo) {
        // Check if selection is already in the list
        val existingIndex = recentlyViewedNotes.indexOf(note)
        if (existingIndex == -1) {
            // it isn't in the list...
            // Add new one to beginning of list and remove any beyond max we want to keep
            recentlyViewedNotes.add(0, note)
            for (index in recentlyViewedNotes.lastIndex downTo maxRecentlyViewedNotes)
                recentlyViewedNotes.removeAt(index)
        } else {
            // it is in the list...
            // Shift the ones above down the list and make it first member of the list
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewedNotes[index + 1] = recentlyViewedNotes[index]
            recentlyViewedNotes[0] = note
        }
    }

    fun saveState(outState: Bundle) {
        println("Saving state for ${recentlyViewedNotes.size} elements")
        val noteIds = DataManager.noteIdsAsIntArray(recentlyViewedNotes)
        outState.putIntegerArrayList(recentlyViewedNotesName, noteIds)
    }

    fun restoreState(savedInstanceState: Bundle) {
        val noteIds = savedInstanceState.getIntegerArrayList(recentlyViewedNotesName)
        println("Loading state for ${noteIds?.size} elements")
        val noteList = noteIds?.let { DataManager.loadNotes(it) }
        noteList?.let { recentlyViewedNotes.addAll(it) }
    }
}
package com.montogo.notekeeper

object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }

    private fun initializeNotes() {
        notes.addAll(listOf(
            NoteInfo(CourseInfo("android_intents", "Android Programming with Intents"), "My notes", "Here is my notes."),
            NoteInfo(CourseInfo(title="Java Fundamentals: The Java Language", courseId = "java_lang"), "My notes about java fundamentals", "Here is my notes for java fundamentals."),
            NoteInfo(CourseInfo("java_core", "Java Fundamentals: The Core Platform"), "Core Java fundamentals is quite simple", "Simplicity is based on OO however there is lots of boiler plate code which clouds the effort"),
            NoteInfo(CourseInfo(courseId = "android_async", title="Android Async Programming and Services"), "Async stuff", "Who likes async, avoid threading it sucks")
        ))
    }

    private fun initializeCourses() {
        courses.putAll(mapOf(
            "android_intents" to CourseInfo("android_intents", "Android Programming with Intents"),
            "android_async" to CourseInfo(courseId = "android_async", title="Android Async Programming and Services"),
            "java_lang" to CourseInfo(title="Java Fundamentals: The Java Language", courseId = "java_lang"),
            "java_core" to CourseInfo("java_core", "Java Fundamentals: The Core Platform")
        ))
    }
}
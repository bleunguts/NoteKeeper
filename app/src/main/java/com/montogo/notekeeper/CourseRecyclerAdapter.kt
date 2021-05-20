package com.montogo.notekeeper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class CourseRecyclerAdapter(private val context: Context, private val courses: List<CourseInfo>) :
    RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_course_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = this.courses.get(position)
        holder.courseTitle.text = course.title
        holder.coursePosition = position
    }

    override fun getItemCount(): Int {
        return this.courses.size
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val courseTitle = view.findViewById<TextView>(R.id.textCourse2)
        var coursePosition: Int = 0
        init {
            view.setOnClickListener{
                Snackbar.make(it, courses.get(coursePosition).title, Snackbar.LENGTH_LONG)
            }
        }
    }
}
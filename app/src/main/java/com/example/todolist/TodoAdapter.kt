package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter (
    private val todos: MutableList<Todo> // Class we created in Todo.kt
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() { // need to define functions tha tevery RecyclerView needs.

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) //views from xml

    /* Add todo item at the end of list and notify index */
    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    /* Parse through todos list and remove todo if checkbox is checked */
    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    /* Strike through if checked, inverse if unchecked */
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    /* Override functions: needed to implement recycle view */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate( // inflate xml to real view.
                R.layout.item_todo, // R for resources
                parent,
                false
            )
        )
    }

    /* Get item count, needed for recycler view to keep track */
    override fun getItemCount(): Int {
        return todos.size
    }

    /* Set data of todo list to text view / checkbox */
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {// create a shortcut
            tvTodoTitle.text = curTodo.title // 'kotlin-android-extensions' plugin made import work.
            cbDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked -> // when checkbox status is changed
                toggleStrikeThrough(tvTodoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }
}
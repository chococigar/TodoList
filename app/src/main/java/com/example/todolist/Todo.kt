package com.example.todolist

/* data class for just holding data */
data class Todo (
    val title: String,
    var isChecked: Boolean = false // var, not val.
)
package dev.pit.todolist

import java.io.Serializable

data class Task(val name: String, var isCompleted: Boolean = false) : Serializable

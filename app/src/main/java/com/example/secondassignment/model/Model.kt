package com.example.secondassignment.model

object Model {
    private val students = mutableListOf<Student>()

    fun getAll(): List<Student> = students
    fun get(key: String): Student? = students.find { it.key == key }

    fun add(s: Student) { students.add(s) }

    fun update(updated: Student) {
        val i = students.indexOfFirst { it.key == updated.key }
        if (i != -1) students[i] = updated
    }

    fun delete(key: String) {
        students.removeAll { it.key == key }
    }

    fun toggleChecked(key: String) {
        get(key)?.let { it.isChecked = !it.isChecked }
    }
}
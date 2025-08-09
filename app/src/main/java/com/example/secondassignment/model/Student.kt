package com.example.secondassignment.model

import com.example.secondassignment.R
import java.util.UUID

data class Student(
    val key: String = UUID.randomUUID().toString(),
    var name: String,
    var id: String,
    var isChecked: Boolean = false,
    var photoRes: Int = R.drawable.ic_student
)

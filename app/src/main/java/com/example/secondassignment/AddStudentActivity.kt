package com.example.secondassignment

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.secondassignment.model.Model
import com.example.secondassignment.model.Student

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student_activity)

        val nameEt = findViewById<EditText>(R.id.nameEt)
        val idEt   = findViewById<EditText>(R.id.idEt)
        val checkedCb = findViewById<CheckBox>(R.id.checkedCb)

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val name = nameEt.text.toString().trim()
            val sid  = idEt.text.toString().trim()

            if (name.isEmpty() || sid.isEmpty()) {
                nameEt.error = if (name.isEmpty()) "Required" else null
                idEt.error   = if (sid.isEmpty()) "Required" else null
                return@setOnClickListener
            }

            Model.add(Student(name = name, id = sid, isChecked = checkedCb.isChecked))
            finish()
        }

        findViewById<Button>(R.id.btnCancel).setOnClickListener { finish() }
    }
}
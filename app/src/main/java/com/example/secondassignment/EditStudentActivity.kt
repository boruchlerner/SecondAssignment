package com.example.secondassignment

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.secondassignment.model.Model

class EditStudentActivity : AppCompatActivity() {

    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_student_activity)

        key = intent.getStringExtra(EXTRA_STUDENT_KEY) ?: run { finish(); return }
        val s = Model.get(key) ?: run { finish(); return }

        val photoIv   = findViewById<ImageView>(R.id.photoIv)
        val nameEt    = findViewById<EditText>(R.id.nameEt)
        val idEt      = findViewById<EditText>(R.id.idEt)
        val checkedCb = findViewById<CheckBox>(R.id.checkedCb)

        photoIv.setImageResource(s.photoRes)
        nameEt.setText(s.name)
        idEt.setText(s.id)
        checkedCb.isChecked = s.isChecked

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val name = nameEt.text.toString().trim()
            val sid  = idEt.text.toString().trim()
            if (name.isEmpty() || sid.isEmpty()) {
                nameEt.error = if (name.isEmpty()) "Required" else null
                idEt.error   = if (sid.isEmpty()) "Required" else null
                return@setOnClickListener
            }

            val updated = s.copy(name = name, id = sid, isChecked = checkedCb.isChecked)
            Model.update(updated)
            finish()
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            Model.delete(key)
            finish()
        }

        findViewById<Button>(R.id.btnCancel).setOnClickListener { finish() }
    }
}
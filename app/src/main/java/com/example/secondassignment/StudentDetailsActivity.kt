package com.example.secondassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.secondassignment.model.Model

class StudentDetailsActivity : AppCompatActivity() {

    private var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_details_activity)

        key = intent.getStringExtra(EXTRA_STUDENT_KEY) ?: run { finish(); return }

        findViewById<Button>(R.id.btnEdit).setOnClickListener {
            startActivity(Intent(this, EditStudentActivity::class.java)
                .putExtra(EXTRA_STUDENT_KEY, key))
        }
    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    private fun bind() {
        val s = Model.get(key!!) ?: run { finish(); return }
        findViewById<ImageView>(R.id.photoIv).setImageResource(s.photoRes)
        findViewById<TextView>(R.id.nameTv).text = s.name
        findViewById<TextView>(R.id.idTv).text = s.id
        findViewById<TextView>(R.id.checkedTv).text = if (s.isChecked) "Yes" else "No"
    }
}
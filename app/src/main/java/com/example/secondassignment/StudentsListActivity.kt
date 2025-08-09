package com.example.secondassignment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secondassignment.model.Model
import com.example.secondassignment.model.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val EXTRA_STUDENT_KEY = "student_key"

class StudentsListActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.students_list_activity)

        recycler = findViewById(R.id.students_recycler_view)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = StudentsAdapter(
            onRowClick = { key ->
                startActivity(Intent(this, StudentDetailsActivity::class.java)
                    .putExtra(EXTRA_STUDENT_KEY, key))
            },
            onCheckToggle = { key ->
                Model.toggleChecked(key)
                adapter.submit(Model.getAll())
            }
        )
        recycler.adapter = adapter


        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val students = Model.getAll()
        adapter.submit(students)

        val emptyView = findViewById<TextView>(R.id.emptyView)

        if (students.isEmpty()) {
            emptyView.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        }
    }
}

private class StudentsAdapter(
    private val onRowClick: (String) -> Unit,
    private val onCheckToggle: (String) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.VH>() {

    private val items = mutableListOf<Student>()

    fun submit(list: List<Student>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val photo: ImageView = v.findViewById(R.id.student_row_image_view)
        val name: TextView = v.findViewById(R.id.student_row_name_text_view)
        val sid: TextView = v.findViewById(R.id.student_row_id_text_view)
        val cb: CheckBox = v.findViewById(R.id.student_row_check_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_row, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val s = items[pos]
        h.name.text = s.name
        h.sid.text = s.id
        h.photo.setImageResource(R.drawable.ic_student)

        // avoid recycled listener glitches
        h.cb.setOnCheckedChangeListener(null)
        h.cb.isChecked = s.isChecked
        h.cb.setOnCheckedChangeListener { _, _ -> onCheckToggle(s.key) }

        h.itemView.setOnClickListener { onRowClick(s.key) }
    }

    override fun getItemCount() = items.size
}/*

package com.example.secondassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secondassignment.model.Model
import com.example.secondassignment.model.Student

class StudentsListActivity : AppCompatActivity() {

    var students: MutableList<Student>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.students_list_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // create layout
        // create adapter
        // create view holder

        students = Model.shared.students
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.students_recycler_view)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = StudentsRecyclerAdapter(students)
        recyclerView.adapter = adapter
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        var nameTextView: TextView? = null
        var idTextView: TextView? = null
        var checkBox: CheckBox? = null
        var student: Student? = null

        init {
            nameTextView  = itemView.findViewById(R.id.student_row_name_text_view)
            idTextView = itemView.findViewById(R.id.student_row_id_text_view)
            checkBox = itemView.findViewById(R.id.student_row_check_box)

            checkBox?.apply {
                setOnClickListener { view ->
                    (tag as? Int)?.let { tag ->
                        student?.isChecked = (view as? CheckBox)?.isChecked ?: false
                    }
                }
            }
        }

        fun bind(student: Student?, position: Int) {
            this.student = student
            nameTextView?.text = student?.name
            idTextView?.text = student?.id
            checkBox?.apply {
                isChecked = student?.isChecked ?: false
                tag = position // save position in tag
            }
        }

    }


    class StudentsRecyclerAdapter(private val students: List<Student>?): RecyclerView.Adapter<StudentViewHolder>(){

        override fun getItemCount(): Int = students?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val inflation = LayoutInflater.from(parent.context)
            val view = inflation.inflate(
                R.layout.student_list_row,
                parent,
                false
            )
            return StudentViewHolder(view)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bind(students?.get(position), position)
        }

    }
}

*/

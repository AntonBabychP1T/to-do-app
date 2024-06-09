package dev.pit.todolist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var item: EditText
    private lateinit var add: Button
    private lateinit var listView: ListView

    var itemList = ArrayList<Task>()

    val fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        item = findViewById(R.id.editTextInput)
        add = findViewById(R.id.buttonAdd)
        listView = findViewById(R.id.listy)

        itemList = fileHelper.readData(this)

        val taskAdapter = TaskAdapter(this, itemList)
        listView.adapter = taskAdapter

        add.setOnClickListener {
            val itemName: String = item.text.toString()
            if (itemName.isNotEmpty()) {
                itemList.add(Task(itemName))
                item.setText("")
                fileHelper.writeData(itemList, applicationContext)
                taskAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Data successfully added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show()
            }
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val task = itemList[position]
            task.isCompleted = !task.isCompleted
            itemList.removeAt(position)
            if (task.isCompleted) {
                itemList.add(task)
            } else {
                itemList.add(0, task)
            }
            taskAdapter.notifyDataSetChanged()
            fileHelper.writeData(itemList, this)
        }
    }
}
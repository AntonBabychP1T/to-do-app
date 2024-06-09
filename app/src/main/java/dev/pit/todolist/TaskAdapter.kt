package dev.pit.todolist

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

class TaskAdapter(context: Context, private val tasks: ArrayList<Task>) :
    ArrayAdapter<Task>(context, 0, tasks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val task = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textView = view.findViewById<TextView>(R.id.itemText)

        textView.text = task?.name
        checkBox.isChecked = task?.isCompleted ?: false

        if (task?.isCompleted == true) {
            textView.setTextColor(Color.GRAY)
            textView.alpha = 0.5f
        } else {
            textView.setTextColor(Color.BLACK)
            textView.alpha = 1.0f
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            task?.isCompleted = isChecked
            notifyDataSetChanged()
            FileHelper().writeData(tasks, context)
        }

        return view
    }
}
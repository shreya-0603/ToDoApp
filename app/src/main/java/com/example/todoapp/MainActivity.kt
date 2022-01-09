package com.example.todoapp

import android.widget.Button


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: taskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val onLongClickListener = object : taskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()
            }


        }

        //findViewById<Button>(R.id.button).setOnClickListener{
          //  Log.i("caren","User clicked on button")

        loadItems()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = taskItemAdapter(listOfTasks, onLongClickListener)
        recyclerView.adapter= adapter
        recyclerView.layoutManager= LinearLayoutManager(this)
        val inputTextField= findViewById<EditText>(R.id.editTextTextPersonName)
        findViewById<Button>(R.id.button).setOnClickListener{
            val userInputtedTask= inputTextField.text.toString()
            listOfTasks.add(userInputtedTask)
            adapter.notifyItemInserted(listOfTasks.size - 1)
            inputTextField.setText("")
        }
    }
    fun getDataFile() : File{
        return File(filesDir, "data.txt")
    }
    fun loadItems()
    {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch (ioException:IOException)
        {
            ioException.printStackTrace()
        }

    }
    fun saveItems()
    {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch (ioException: IOException)
        {
            ioException.printStackTrace()
        }

    }
}

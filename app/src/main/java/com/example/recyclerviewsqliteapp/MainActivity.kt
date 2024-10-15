package com.example.recyclerviewsqliteapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private var userList: ArrayList<UserModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        sqliteHelper = SQLiteHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            if (name.isNotEmpty()) {
                val user = UserModel(name = name)
                val status = sqliteHelper.insertUser(user)
                if (status > -1) {
                    Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                    loadUsers()
                } else {
                    Toast.makeText(this, "Failed to Add User", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            }
        }

        loadUsers()
    }

    private fun loadUsers() {
        userList = sqliteHelper.getAllUsers()
        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter
    }
}

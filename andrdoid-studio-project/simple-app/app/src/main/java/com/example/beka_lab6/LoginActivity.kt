package com.example.beka_lab6

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Variables
        val userEmail: EditText = findViewById(R.id.email)
        val userPassword: EditText = findViewById(R.id.password)
        val button: Button = findViewById(R.id.button)

        // Sign In button
        button.setOnClickListener {
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()

            // Validate fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Some of the fields are empty", Toast.LENGTH_LONG).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_LONG).show()
            } else {
                val db = DatabaseHelper(this, null)

                // Checks if the user with the given email and password exists in the database
                if (db.isUserValid(email, password)) {
                    Toast.makeText(this, "$email || $password", Toast.LENGTH_LONG).show()
                    // Successful login
                    Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()

                    // Stores the user's email
                    val sharedPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.putString("loggedInUserEmail", email)
                    editor.apply()

                    // Navigates to the ProfileActivity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)

                } else {
                    // Invalid credentials
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show()
                }

                userEmail.text.clear()
                userPassword.text.clear()
            }
        }

        // Signs up TextView on Click Listener
        val bottomLabel2: TextView = findViewById(R.id.bottom_label2)
        bottomLabel2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    // Function to validate email
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex()) // regular expression
        /*
        example:
        Regular Expression: ^\d{3}-\d{2}-\d{4}$
        String: "123-45-6789"
        */
    }
}
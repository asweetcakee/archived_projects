package com.example.beka_lab6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Variables
        val userName: EditText = findViewById(R.id.person_name)
        val userEmail: EditText = findViewById(R.id.email)
        val userPhone: EditText = findViewById(R.id.phone)
        val userPassword: EditText = findViewById(R.id.password)
        val userPasswordConfirmation: EditText = findViewById(R.id.password_confirmation)
        val button: Button = findViewById(R.id.button)

        // Sign Up button
        button.setOnClickListener {
            val name = userName.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val phone = userPhone.text.toString().trim()
            val password = userPassword.text.toString().trim()
            val passwordConfirmation = userPasswordConfirmation.text.toString().trim()

            // Validate fields
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Some of the fields are empty", Toast.LENGTH_LONG).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_LONG).show()
            } else if (!isValidPhone(phone)) {
                Toast.makeText(this, "Phone number is not valid", Toast.LENGTH_LONG).show()
            } else if (password != passwordConfirmation) {
                Toast.makeText(this, "Passwords do not match. Please try again.", Toast.LENGTH_LONG).show()
            } else {
                val user = User(name, phone, email, password)

                val db = DatabaseHelper(this, null)
                db.addUser(user)
                Toast.makeText(this, "User $name has been added", Toast.LENGTH_LONG).show()

                userName.text.clear()
                userEmail.text.clear()
                userPhone.text.clear()
                userPassword.text.clear()
                userPasswordConfirmation.text.clear()
            }
        }

        // Sign in TextView on Click Listener
        val bottomLabel2: TextView = findViewById(R.id.bottom_label2)
        bottomLabel2.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    // Function to validate email
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        // Checks email pattern
        return email.matches(emailPattern.toRegex()) //regular expression
        /*
        example:
        Regular Expression: ^\d{3}-\d{2}-\d{4}$
        String: "123-45-6789"
        */
    }

    // Function to validate phone number
    private fun isValidPhone(phone: String): Boolean {
        // Removes any non-digit characters
        val numericPhone = phone.replace("[^\\d]".toRegex(), "")
        // Checks if the numeric part is between 10 and 15 digits
        return numericPhone.length in 10..15
    }

}
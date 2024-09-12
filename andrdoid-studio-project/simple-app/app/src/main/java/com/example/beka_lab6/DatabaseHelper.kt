package com.example.beka_lab6

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """CREATE TABLE table_users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_name TEXT,
                email TEXT,
                phone TEXT,
                password TEXT
            )
        """
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS table_users")
        onCreate(db)
    }

    // Adds a new user to the table table_users
    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("user_name", user.name)
        values.put("email", user.email)
        values.put("phone", user.phone)
        values.put("password", user.password)
        db.insert("table_users", null, values)
        db.close()
    }

    // Checks if a user with the provided email and password exists in the db
    fun isUserValid(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM table_users WHERE email = ? AND password = ?", arrayOf(email, password))
        val isValid = cursor.moveToFirst()
        cursor.close()
        db.close()
        return isValid
    }

    // Function fetches the user's name based on their credentials
    fun getUserNameForLoggedInUser(email: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT user_name FROM table_users WHERE email = ?",arrayOf(email))
        if (cursor.moveToFirst()) {
            val userName = cursor.getString(cursor.getColumnIndex("user_name"))
            cursor.close()
            db.close()
            return userName
        }

        cursor.close()
        db.close()
        return null
    }

}

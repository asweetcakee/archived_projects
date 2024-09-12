package com.example.beka_lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Retrieves cart items and total amount from intent extras
        val cartItems = intent.getParcelableArrayListExtra<Item>("cartItems")
        val totalAmount = intent.getDoubleExtra("totalAmount", 0.0)

        Log.d("CartActivity", "Cart Items: $cartItems")

        // Initializes and sets up the RecyclerView
        val cartRecyclerView: RecyclerView = findViewById(R.id.recycler_cart_items)
        cartRecyclerView.layoutManager = LinearLayoutManager(this)

        val cartAdapter = if (cartItems != null) {
            CartItemsAdapter(cartItems)
        } else {
            CartItemsAdapter(emptyList()) // Provides an empty list or handle the null case as needed
        }

        // Tells RecyclerView to use custom CartItemsAdapter to populate and display its content
        cartRecyclerView.adapter = cartAdapter

        // Displays the total amount in a TextView
        val totalAmountTextView: TextView = findViewById(R.id.text_total_price)
        totalAmountTextView.text = "Total Price: $${"%.2f".format(totalAmount)}"
    }
}





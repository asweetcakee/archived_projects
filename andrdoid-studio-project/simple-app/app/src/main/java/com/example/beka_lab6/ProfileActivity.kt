package com.example.beka_lab6

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList



class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Finds the desired TextView to change show logged user name
        val userNameTextView = findViewById<TextView>(R.id.pa_txtV_user_name)

        // Retrieves the user's email from SharedPreferences
        val sharedPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val loggedInUserEmail = sharedPrefs.getString("loggedInUserEmail", "")

        if (!loggedInUserEmail.isNullOrEmpty()) {
            // Retrieves the user's name from the database based on their email
            val db = DatabaseHelper(this, null)
            val loggedInUserName = db.getUserNameForLoggedInUser(loggedInUserEmail)

            if (loggedInUserName != null) {
                userNameTextView.text = loggedInUserName
            }
        }

        val cartItems = mutableListOf<Item>() // Tracks items in the cart
        lateinit var itemsAdapter: ItemsAdapter

        val itemsList: RecyclerView = findViewById(R.id.recV_items_list)
        val items = arrayListOf<Item>()

        // Add items to the list
        items.add(Item(1, "sofa", "Sofa", "Comfy sofa", "This sofa was made by a famous Italian manufacturer", 299.79))
        items.add(Item(2, "bed", "Bed", "Queen-sized bed", "Upgrade your sleep experience with our luxurious Queen Size Bed", 349.99))
        items.add(Item(3, "table", "Table", "Dining table", "Elevate your dining and entertainment space with our exquisite Table", 199.29))
        items.add(Item(4, "chair", "Chair", "Elegant Chair", "Add elegance to your room with this comfortable chair", 149.99))
        items.add(Item(5, "lamp", "Lamp", "Modern Lamp", "Illuminate your space with this sleek and modern lamp", 59.99))
        items.add(Item(6, "couch", "Couch", "Stylish Couch", "Relax and unwind with this stylish and comfortable couch", 399.99))
        items.add(Item(7, "desk", "Desk", "Functional Desk", "Boost your productivity with this functional desk. It offers ample workspace for your tasks.", 249.99))
        items.add(Item(8, "wardrobe", "Wardrobe", "Spacious Wardrobe", "Organize your clothes and accessories in style with this spacious wardrobe.", 299.99))
        items.add(Item(9, "nightstand", "Nightstand", "Elegant Nightstand", "Complete your bedroom decor with this elegant nightstand. It provides convenient storage by your bedside.", 79.99))
        items.add(Item(10, "mirror", "Mirror", "Decorative Mirror", "Enhance your room's ambiance with this decorative mirror. It adds a touch of sophistication to your space.", 69.99))


        // Items will be displayed in a grid with 2 columns
        itemsList.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        // Initializes ItemsAdapter for RecyclerView
        // "item ->" lambda expression, defines what should happen when the user interacts with an item.
        // Defines behavior when an item's button is clicked
        itemsAdapter = ItemsAdapter(items, this) { item ->
            item.quantityInCart++ // Increments the quantity of an item
            item.totalItemPrice = item.price * item.quantityInCart // Calculates total price for items
            cartItems.add(item)
            itemsAdapter.notifyDataSetChanged() // Triggers RecyclerView to update and display changes (updates button's text after each interaction)
        }

        // Tells RecyclerView to use custom itemsAdapter to populate and display its content
        itemsList.adapter = itemsAdapter


        val cartTextView: TextView = findViewById(R.id.item_list_cart)
        cartTextView.setOnClickListener {
            // Launches the cart activity if there are items in the cart
            if (cartItems.isNotEmpty()) {

                // Removes duplicates based on the item's unique ID
                // "{ it.id }" lambda expression for "distinctBy", that removes duplicate
                val uniqueCartItems = cartItems.distinctBy { it.id }

                val intent = Intent(this, CartActivity::class.java)
                intent.putParcelableArrayListExtra("cartItems", ArrayList(uniqueCartItems)) // Sends uniqueCartItems list with the key "cartItems"
                intent.putExtra("totalAmount", calculateTotalAmount(uniqueCartItems)) // Sends uniqueCartItems list with the key "totalAmount"
                startActivity(intent)

            } else {
                // In case the cart is empty
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Calculate the total amount of items in the cart
    private fun calculateTotalAmount(cartItems: List<Item>): Double {
        var totalAmount = 0.0
        for (item in cartItems) {
            totalAmount += item.price * item.quantityInCart
        }
        return totalAmount
    }
}



